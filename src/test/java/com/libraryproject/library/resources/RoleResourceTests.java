package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.Role;
import com.libraryproject.library.services.RoleService;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(value = RoleResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class RoleResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoleService service;

    public Role role;
    public List<Role> roleList;
    public Long existingId;
    public Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        role = Factory.createRole();
        roleList = List.of(role);
        existingId = 1L;
        nonExistingId = 100L;

        when(service.findAll()).thenReturn(roleList);
        when(service.findById(existingId)).thenReturn(role);
        when(service.findById(existingId)).thenReturn(role);
        doThrow(ResourceNotFoundException.class).when(service).findById(nonExistingId);
    }

    @Test
    public void findAllShouldReturnRoleList() throws Exception {
        ResultActions result = mockMvc.perform(get("/roles")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("[0].id").exists());
        result.andExpect(jsonPath("[0].authority").exists());
    }

    @Test
    public void findByIdShouldReturnRoleWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/roles/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.authority").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/roles/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

}
