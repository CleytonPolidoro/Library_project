package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.dto.OrderMinDTO;
import com.libraryproject.library.services.OrderService;
import com.libraryproject.library.services.exceptions.DateTimeException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = OrderResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class OrderResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderDTO orderDto;
    private List<OrderMinDTO> orderMinList;
    private OrderMinDTO orderMinDTO;
    private PageImpl<OrderMinDTO> orderMinPage;
    String minDate;
    String invalidMinDate;
    String maxDate;
    Long existingId;
    Long nonExistingId;


    @BeforeEach
    void setUp() throws Exception{
        orderDto = Factory.createOrderDTO();

        orderMinDTO = Factory.createOrderMinDTO();
        orderMinList = List.of(orderMinDTO);
        orderMinPage = new PageImpl<>(List.of(orderMinDTO));
        minDate = "2024-10-05";
        maxDate = "2025-02-24";
        invalidMinDate = "2024/10/05";
        existingId = 1L;
        nonExistingId = 100L;

        when(service.myOrders()).thenReturn(orderMinList);
        when(service.findAll(any())).thenReturn(orderMinPage);
        when(service.findBetween(any(), eq(minDate), eq(maxDate))).thenReturn(orderMinPage);
        doThrow(DateTimeException.class).when(service).findBetween(any(), eq(invalidMinDate), eq(maxDate));
        doThrow(ResourceNotFoundException.class).when(service).findById(nonExistingId);
        when(service.findById(existingId)).thenReturn(orderDto);
        when(service.insert(any())).thenReturn(orderDto);

    }

    @Test
    public void myOrdersShouldReturnListOrderMin() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/me")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("[0].id").exists());
        result.andExpect(jsonPath("[0].moment").exists());
        result.andExpect(jsonPath("[0].status").exists());
    }

    @Test
    public void findAllShouldReturnPageOrderMin() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_ATOM_XML)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.empty").isNotEmpty());
    }

    @Test
    public void findBetweenShouldReturnPageOrderMinWhenFormatDateIsValid() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/between")
                .param("minDate", minDate)
                .param("maxDate", maxDate)
                .contentType(MediaType.APPLICATION_ATOM_XML)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.empty").isNotEmpty());
    }

    @Test
    public void findBetweenShouldReturnUnprocessableEntityWhenFormatDateIsInvalid() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/between")
                .param("minDate", invalidMinDate)
                .param("maxDate", maxDate)
                .contentType(MediaType.APPLICATION_ATOM_XML)
        );
        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void findByIdShouldReturnOrderDtoWhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/{id}", existingId)
                .contentType(MediaType.APPLICATION_ATOM_XML)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.moment").exists());
        result.andExpect(jsonPath("$.status").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/{id}", nonExistingId)
                .contentType(MediaType.APPLICATION_ATOM_XML)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnOrderDto() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(orderDto);

        ResultActions result = mockMvc.perform(post("/orders")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.moment").exists());
        result.andExpect(jsonPath("$.status").exists());
    }
}
