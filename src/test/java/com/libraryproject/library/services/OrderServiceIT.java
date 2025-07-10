package com.libraryproject.library.services;

import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.dto.OrderMinDTO;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.repositories.OrderItemRepository;
import com.libraryproject.library.repositories.OrderRepository;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.exceptions.DateTimeException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class OrderServiceIT {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderItemRepository itemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @MockitoBean
    private UserService userService;


    public Pageable pageable;
    public String minDate, maxDate, minDateInvalid, maxDateInvalid;
    public Long existingId, nonExistingId;
    public OrderDTO orderDto;
    public Optional<User> user;
    public UserDTO userDTO;

    @BeforeEach
    void setUp() throws Exception {
        pageable = PageRequest.of(0,10);
        minDate = "2024-10-05";
        maxDate = "2025-02-24";
        minDateInvalid = "2024/10-05";
        maxDateInvalid = "2025/02/24";
        existingId = 1L;
        nonExistingId = 100L;
        orderDto = Factory.createOrderDTO();
        user = userRepository.findByEmail("alex@gmail.com");
        userDTO = new UserDTO(userRepository.findByEmail("maria@gmail.com").get());

        Mockito.when(userService.authenticated()).thenReturn(user);
        Mockito.when(userService.getMe()).thenReturn(userDTO);
    }

    @Test
    public void findBetweenShouldReturnPageWhenDateFormatAreValid(){
        Page<OrderMinDTO> result = service.findBetween(pageable, minDate, maxDate);

        Assertions.assertTrue(result.hasContent());
    }

    @Test
    public void findBetweenShouldThrowsDateTimeParseExceptionWhenDateFormatAreInvalid(){
        Assertions.assertThrows(DateTimeException.class, () -> {
            service.findBetween(pageable, minDateInvalid, maxDateInvalid);
        });
    }

    @Test
    public void findAllShouldReturnPage(){
        Page<OrderMinDTO> result = service.findAll(pageable);

        Assertions.assertTrue(result.hasContent());
    }

    @Test
    public void findByIdShouldReturnOrderDtoWhenIdExists(){
        OrderDTO result = service.findById(existingId);
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnOrderDto(){
        orderDto.setId(null);

        OrderDTO result = service.insert(orderDto);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(result.getId(), repository.count());

    }

    @Test
    public void myOrdersShouldReturnList(){
        List<OrderMinDTO> result = service.myOrders();

        Assertions.assertFalse(result.isEmpty());
    }
}
