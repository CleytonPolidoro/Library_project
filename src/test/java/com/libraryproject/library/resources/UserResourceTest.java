package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import com.libraryproject.library.repositories.UserRepository;
import com.libraryproject.library.services.RoleService;
import com.libraryproject.library.services.UserService;
import com.libraryproject.library.services.exceptions.DatabaseException;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(value = UserResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService service;

    @MockitoBean
    private UserRepository repository;

    @MockitoBean
    private RoleService roleService;

    public UserDTO userDto;
    public PageImpl<UserDTO> pageUserDto;
    public Long existingId;
    public Long nonExistingId;
    public Long dependentId;
    public UserInsertDTO userInsert;
    public UserUpdateDTO userUpdate;

    @BeforeEach
    void setUp() throws Exception{

        userDto = Factory.createUserDTO();
        pageUserDto = new PageImpl<>(List.of(userDto));
        existingId = 1L;
        dependentId = 2L;
        nonExistingId = 100L;
        userInsert = Factory.createUserInsertDTO();
        userUpdate = Factory.createUserUpdateDTO();

        when(service.getMe()).thenReturn(userDto);
        when(service.findAll(any())).thenReturn(pageUserDto);
        when(service.findById(existingId)).thenReturn(userDto);
        doThrow(ResourceNotFoundException.class).when(service).findById(nonExistingId);
        doThrow(ResourceNotFoundException.class).when(service).deleteById(nonExistingId);
        doThrow(DatabaseException.class).when(service).deleteById(dependentId);
        doNothing().when(service).deleteById(existingId);
        when(service.insert(any())).thenReturn(userDto);
        when(service.update(eq(existingId), any())).thenReturn(userDto);
        doThrow(ResourceNotFoundException.class).when(service).update(eq(nonExistingId), any());

    }

    @Test
    public void getMeShouldReturnUser() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phone").exists());
    }

    @Test
    public void findAllShouldReturnPageUser() throws Exception{
        ResultActions result = mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.empty").value(false));
    }

    @Test
    public void findByIdShouldReturnUserWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phone").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnCreated() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(userInsert);

        ResultActions result = mockMvc.perform(post("/users")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phone").exists());

    }

    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(delete("/users/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnBadRequestWhenIdIsDependent() throws Exception {
        ResultActions result = mockMvc.perform(delete("/users/{id}", dependentId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(delete("/users/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNoContent());
    }

    @Test
    public void updateShouldReturnUserDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(userUpdate);

        ResultActions result = mockMvc.perform(put("/users/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.email").exists());
        result.andExpect(jsonPath("$.phone").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(userUpdate);

        ResultActions result = mockMvc.perform(put("/users/{id}", nonExistingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }
}
