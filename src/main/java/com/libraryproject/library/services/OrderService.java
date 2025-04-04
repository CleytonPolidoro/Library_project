package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.ClientDTO;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.dto.OrderItemDTO;
import com.libraryproject.library.entities.dto.OrderMinDTO;
import com.libraryproject.library.entities.enums.OrderStatus;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.repositories.OrderItemRepository;
import com.libraryproject.library.repositories.OrderRepository;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.DateTimeException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderItemRepository itemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    public Page<OrderMinDTO> findBetween(Pageable pageable, String minDate, String maxDate){
          try {
              LocalDate today = LocalDate.now();

              Instant max = maxDate.equals("") ? today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                      : LocalDate.parse(maxDate).plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

              Instant min = minDate.equals("") ? today.minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
                      : LocalDate.parse(minDate).atStartOfDay(ZoneId.systemDefault()).toInstant();

              Page<OrderProjection> page = repository.findOrdersBetweenDates(min, max, pageable);
              List<OrderItemProjection> list = repository.findOrdersAndItems(page.map(x -> x.getId().intValue()).toList());

              return convertProjectionToDto(page, list);
          } catch (DateTimeParseException e){
              throw new DateTimeException("Use DD-MM-YYYY' format");
          }
    }


    @Transactional(readOnly = true)
    public Page<OrderMinDTO> findAll(Pageable pageable){
        Page<OrderProjection> page = repository.searchAll(pageable);
        List<OrderItemProjection> list = repository.findOrdersAndItems(page.map(x -> x.getId().intValue()).toList());
        return convertProjectionToDto(page, list);
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        List<Integer> list = new ArrayList<>();
        list.add(id.intValue());

        Order order = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Resource not found. Id "+id));

        List<OrderItemProjection> result = repository.findOrdersAndItems(list);
        OrderDTO dto = new OrderDTO(order);
//        dto.getItems().addAll(result.stream().map(x -> new OrderItemDTO(x)).toList());

//        authService.validateSelfOrAdmin(order.getClientId());

        return dto;
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order order = new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated().get();
        order.setClient(user);

        for (OrderItemDTO item : dto.getItems()) {
            Book book = bookRepository.getReferenceById(item.getBookId());
            OrderItem orderItem = new OrderItem(order, book, item.getQuantity(), book.getPrice());
            order.getItems().add(orderItem);
        }

        repository.save(order);
        itemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }


    private Page<OrderMinDTO> convertProjectionToDto(Page<OrderProjection> page, List<OrderItemProjection> list){
        List<OrderMinDTO> page3 = new ArrayList<>();

        for(OrderProjection order : page){
            for (OrderItemProjection items : list){
                if(order.getId().equals(items.getOrderId())){
                    page3.add(new OrderMinDTO(order.getId(), order.getClientId(), order.getMoment(), order.getStatus(), items));
                }
            }
        }

        Page<OrderMinDTO> page4 = page.map(x -> new OrderMinDTO(x));

        for(OrderMinDTO order : page4){
            for(OrderMinDTO dto : page3){
                if(order.getId().equals(dto.getId())){
                    order.getItems().addAll(dto.getItems());
                }
            }
        }
        return page4;
    }
}
