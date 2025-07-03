package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.dto.GenderDTO;
import com.libraryproject.library.services.GenderService;
import com.libraryproject.library.services.exceptions.DatabaseException;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(value = GenderResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class GenderResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GenderService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Gender gender;
    private GenderDTO dto;
    private PageImpl<GenderDTO> pageDto;
    private String genderName;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        gender = Factory.createGender();
        dto = Factory.createGenderDTO();
        pageDto = new PageImpl<>(List.of(dto));
        genderName = gender.getName();
        existingId = 1L;
        nonExistingId = 100L;

        when(service.findAll(any(), eq(genderName))).thenReturn(pageDto);
        when(service.findById(existingId)).thenReturn(dto);
        when(service.findById(existingId)).thenReturn(dto);
        when(service.save(any())).thenReturn(dto);
        doThrow(DatabaseException.class).when(service).findById(nonExistingId);

    }

    @Test
    public void findAllShouldReturnPage() throws Exception{

        ResultActions result = mockMvc.perform(get("/genders")
                .param("gender", genderName)
                .accept(MediaType.APPLICATION_JSON)

        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("empty").value("false"));
        result.andExpect(jsonPath("content").exists());
    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/genders/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("id").exists());
        result.andExpect(jsonPath("name").exists());
    }

    @Test
    public void findByIdShouldReturnBadRequestWhenIdDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/genders/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void saveShouldReturnCreated() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc.perform(post("/genders")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
    }
}
