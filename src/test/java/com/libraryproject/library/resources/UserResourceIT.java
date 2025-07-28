package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.UserService;
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

import javax.xml.transform.Result;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserResourceIT {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil token;

    private String username, password, bearerToken;
    private Long existingId, nonExistingId;
    private UserInsertDTO userInsert;
    private UserUpdateDTO userUpdate;

    @BeforeEach
    void setUp()throws Exception{
        username = "admin@gmail.com";
        password = "123456";
        bearerToken = token.obtainAccessToken(mockMvc, username, password);
        existingId = 3L;
        nonExistingId = 100L;
        userInsert = Factory.createUserInsertDTO();
        userUpdate = Factory.createUserUpdateDTO();
    }


    @Test
    public void getMeShouldReturnUserLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/me")
                .header("Authorization", "Bearer " + bearerToken )
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("email").value(username));
    }

    @Test
    public void findAllShouldReturnPageNotEmpty() throws Exception {
        ResultActions result = mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + bearerToken )
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.empty").value(false));

    }

    @Test
    public void findByIdShouldReturnIsOkWhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/users/{id}", existingId)
                .header("Authorization", "Bearer " + bearerToken )
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(jsonPath("id").value(existingId));
        result.andExpect(status().isOk());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/{id}", nonExistingId)
                .header("Authorization", "Bearer " + bearerToken )
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void saveShouldReturnCreatedWhenDataIsValid() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(userInsert);
        Long lastId = repository.count();

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("id").value(lastId+1));
    }

    @Test
    public void saveShouldReturnUnprocessableEntityWhenDataDoesNotValid() throws Exception{
        userInsert.setName("a");

        String jsonBody = objectMapper.writeValueAsString(userInsert);
        Long lastId = repository.count();

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void deleteByIdShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/users/{id}", existingId)
                .header("Authorization", "Bearer " + bearerToken)
                .accept(MediaType.APPLICATION_JSON)
        );


        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/users/{id}", nonExistingId)
                .header("Authorization", "Bearer " + bearerToken)
                .accept(MediaType.APPLICATION_JSON)
        );


        result.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnIsOkWhenDataIsValid() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(userUpdate);

        ResultActions result = mockMvc.perform(put("/users/{id}" , existingId)
                .content(jsonBody)
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
    }

    @Test
    public void UpdateShouldReturnUnprocessableEntityWhenDataDoesNotValid() throws Exception{
        userUpdate.setName("Cl");

        String jsonBody = objectMapper.writeValueAsString(userUpdate);

        ResultActions result = mockMvc.perform(put("/users/{id}", existingId)
                .content(jsonBody)
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void UpdateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception{

        String jsonBody = objectMapper.writeValueAsString(userUpdate);

        ResultActions result = mockMvc.perform(put("/users/{id}", nonExistingId)
                .content(jsonBody)
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }
}
