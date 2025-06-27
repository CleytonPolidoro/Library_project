package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.dto.OrderMinDTO;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.repositories.OrderItemRepository;
import com.libraryproject.library.repositories.OrderRepository;
import com.libraryproject.library.services.exceptions.DateTimeException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
public class OrderServiceTests {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    Pageable pageable;
    OrderProjection orderProjection;
    Page pageOrderProjection;
    OrderItemProjection orderItemProjection;
    List listOrderItemProjection;
    String minDate;
    String maxDate;
    Instant minInstant;
    Instant maxInstant;
    String incorrectDate;
    Long existingId;
    Long nonExistingId;
    OrderDTO orderDto;
    Order order;
    Book book;
    List orderItemList;
    User user;
    UserDTO userDto;

    @BeforeEach
    void setUp() throws Exception{
        pageable = PageRequest.of(0, 10);
        orderProjection = Factory.createOrderProjection();
        pageOrderProjection = new PageImpl<>(List.of(orderProjection));
        orderItemProjection = Factory.createOrderItemDto();
        listOrderItemProjection = List.of(Factory.createOrderItemDto());
        minDate = "2024-10-06";
        maxDate = "2025-02-24";
        minInstant = Instant.parse(minDate+"T00:00:00Z");
        maxInstant = Instant.parse(maxDate+"T00:00:00Z");
        incorrectDate = "2024 10-06";
        existingId = 1L;
        nonExistingId = 100L;
        orderDto = Factory.createOrderDTO();
        order = Factory.createOrder();
        book = Factory.createBook();
        orderItemList = List.of(Factory.createOrderItem());
        user = Factory.createUser();
        userDto = Factory.createUserDTO();

        Mockito.when(orderRepository.findOrdersBetweenDates(
                ArgumentMatchers.any(Instant.class), ArgumentMatchers.any(Instant.class), ArgumentMatchers.any(Pageable.class)
        )).thenReturn(pageOrderProjection);

//        Mockito.doThrow(DateTimeParseException.class).when(orderRepository.findOrdersBetweenDates(incorrectInstant, maxInstant, pageable));

        Mockito.when(orderRepository.searchAll(pageable)).thenReturn(pageOrderProjection);
        Mockito.when(orderRepository.findOrdersAndItems(ArgumentMatchers.any())).thenReturn(listOrderItemProjection);

        Mockito.when(orderRepository.findById(existingId)).thenReturn(Optional.of(order));
        Mockito.when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);

        Mockito.when(bookRepository.getReferenceById(existingId)).thenReturn(book);
        Mockito.when(orderItemRepository.saveAll(ArgumentMatchers.any())).thenReturn(orderItemList);
        Mockito.when(userService.authenticated()).thenReturn(Optional.of(user));
        Mockito.when(userService.getMe()).thenReturn(userDto);
        Mockito.when(orderRepository.findByClientId(user.getId(), pageable)).thenReturn(pageOrderProjection);

    }


    @Test
    public void findBetweenShouldReturnPageOrderMinDtoWhenFormatDateIsValid(){
        Page<OrderMinDTO> result = service.findBetween(pageable, minDate, maxDate);

        Assertions.assertTrue(result.hasContent());

        Mockito.verify(orderRepository).findOrdersBetweenDates(
                ArgumentMatchers.any(Instant.class), ArgumentMatchers.any(Instant.class), ArgumentMatchers.any(Pageable.class)
        );
        Mockito.verify(orderRepository).findOrdersAndItems(ArgumentMatchers.any());
    }

    @Test
    public void findBetweenShouldThrowDateTimeExceptionWhenFormatDateInvalid(){
        Assertions.assertThrows(DateTimeException.class, () -> {
            service.findBetween(pageable, incorrectDate, maxDate);
        });
    }

    @Test
    public void findAllShouldReturnPageOrderMinDto(){
        Page<OrderMinDTO> result = service.findAll(pageable);

        Assertions.assertTrue(result.hasContent());

        Mockito.verify(orderRepository, Mockito.times(1)).searchAll(pageable);
        Mockito.verify(orderRepository, Mockito.times(1)).findOrdersAndItems(ArgumentMatchers.any());

    }

    @Test
    public void findByIdShouldReturnOrderWhenIdExists(){
        OrderDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);

        Mockito.verify(orderRepository).findById(existingId);
    }

    @Test
    public void insertShouldOrderDto(){
        OrderDTO result = service.insert(orderDto);

        Assertions.assertNotNull(result);
        Mockito.verify(orderRepository).save(ArgumentMatchers.any(Order.class));
        Mockito.verify(orderItemRepository).saveAll(ArgumentMatchers.any());
    }

    @Test
    public void myOrdersShouldReturnListOrderMinDto(){
        List<OrderMinDTO> result = service.myOrders();

        Assertions.assertFalse(result.isEmpty());

        Mockito.verify(orderRepository).findByClientId(user.getId(), pageable);
        Mockito.verify(orderRepository).findOrdersAndItems(ArgumentMatchers.any());
    }
}
