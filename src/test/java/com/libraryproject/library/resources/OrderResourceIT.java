package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.services.OrderService;
import com.libraryproject.library.tests.Factory;
import com.libraryproject.library.tests.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderResourceIT {

    @Autowired
    private OrderService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil token;

    private String client, admin, clientPass, adminPass, bearerTokenClient, bearerTokenAdmin;
    private Long existingId, nonExistingId;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() throws Exception{
        client = "maria@gmail.com";
        clientPass = "123456";
        bearerTokenClient = token.obtainAccessToken(mockMvc, client, clientPass);
        admin = "admin@gmail.com";
        adminPass = "123456";
        bearerTokenAdmin = token.obtainAccessToken(mockMvc, admin, adminPass);
        existingId = 1L;
        nonExistingId = 100L;
        orderDTO = Factory.createOrderDTO();
        orderDTO.setId(null);
    }

    @Test
    public void myOrdersShouldReturnOrderListWhenClientIsLoggedIn() throws Exception {

        ResultActions result = mockMvc.perform(get("/orders/me")
                .header("Authorization", "Bearer " + bearerTokenClient)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("[0].clientId").exists());
        result.andExpect(jsonPath("[0].status").exists());
        result.andExpect(jsonPath("[0].id").exists());

    }

    @Test
    public void myOrdersShouldReturnForbiddenWhenAdminIsLoggedIn() throws Exception {

        ResultActions result = mockMvc.perform(get("/orders/me")
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isForbidden());

    }

    @Test
    public void findAllShouldReturnPage()throws Exception{
        ResultActions result = mockMvc.perform(get("/orders")
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("empty").isNotEmpty());
    }

    @Test
    public void findBetweenShouldReturnPageWhenAdminIsLoggedIn() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/between")
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .param("minDate", "2024-10-05")
                .param("maxDate", "2025-02-24")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("pageable").exists());

    }


    @Test
    public void findBetweenShouldReturnUnprocessableEntityWhenDataDoesNotValid() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/between")
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .param("minDate", "2024-10-05")
                .param("maxDate", "abc")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void findBetweenShouldReturnForbiddenWhenClientIsLoggedIn() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/between")
                .header("Authorization", "Bearer " + bearerTokenClient)
                .param("minDate", "2024-10-05")
                .param("maxDate", "2025-02-24")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnForbiddenWhenClientIsLoggedIn() throws Exception {
        ResultActions result = mockMvc.perform(get("/orders/{id}", existingId)
                .header("Authorization", "Bearer " + bearerTokenClient)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isForbidden());
    }

    @Test
    public void findByIdShouldReturnOrderWhenAdminIsLoggedInAndIdExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/{id}", existingId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("id").exists());
        result.andExpect(jsonPath("status").exists());
        result.andExpect(jsonPath("moment").exists());
        result.andExpect(jsonPath("client").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/orders/{id}", nonExistingId)
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void saveShouldReturnForbiddenWhenAdminIsLoggedIn() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(orderDTO);

        ResultActions result = mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + bearerTokenAdmin)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isForbidden());
    }

    @Test
    public void saveShouldReturnCreatedWhenClientIsLoggedInAndDataIsValid() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(orderDTO);

        ResultActions result = mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + bearerTokenClient)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("id").exists());
        result.andExpect(jsonPath("status").exists());
        result.andExpect(jsonPath("moment").exists());
        result.andExpect(jsonPath("client").exists());
    }

    @Test
    public void saveShouldReturnUnprocessableWhenClientIsLoggedIdAndDataDoesNotIsValid() throws Exception{
        orderDTO.getItems().clear();
        String jsonBody = objectMapper.writeValueAsString(orderDTO);

        ResultActions result = mockMvc.perform(post("/orders")
                .header("Authorization", "Bearer " + bearerTokenClient)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnprocessableEntity());
    }
}
