package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.entities.dto.BookMinDTO;
import com.libraryproject.library.services.BookService;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

@WebMvcTest(value = BookResource.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BookResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService service;

    @Autowired
    private ObjectMapper objectMapper;

    private PageImpl<BookMinDTO> pageBookMinDto;
    private BookMinDTO bookMin;
    private String existingTitle;
    private String existingAuthor;
    private String nonExistingAuthor;
    private Long existingId;
    private Long nonExistingId;
    private BookDTO bookDTO;
    private BookDTO dependentBook;

    @BeforeEach
    void setUp() throws Exception {

        bookMin = Factory.createBookMinDTO();
        pageBookMinDto = new PageImpl<>(List.of(bookMin));
        existingTitle = "Diario Estoico";
        existingAuthor = "Ryan Holiday";
        nonExistingAuthor = "Cleyton Polidoro";
        existingId = 1L;
        nonExistingId = 100L;
        bookDTO = Factory.createBookDto();
        dependentBook = Factory.createExistingBookDto();

        when(service.findAll(any(), eq(existingTitle), eq(existingAuthor))).thenReturn(pageBookMinDto);
        when(service.findAll(any(), eq(existingTitle), eq(nonExistingAuthor))).thenReturn(new PageImpl<>(List.of()));
        when(service.findById(existingId)).thenReturn(bookDTO);
        doThrow(ResourceNotFoundException.class).when(service).findById(nonExistingId);
        when(service.insert(any())).thenReturn(bookDTO);
    }

    @Test
    public void findAllShouldReturnPageBookMinDtoWhenParamsExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/books")
                .param("title", existingTitle)
                .param("author", existingAuthor)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("empty").value("false"));
    }

    @Test
    public void findAllShouldReturnPageEmptyWhenParamsDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/books")
                .param("title", existingTitle)
                .param("author", nonExistingAuthor)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("empty").value("true"));
    }

    @Test
    public void findByIdShouldReturnBookDTOWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/books/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.author").exists());
        result.andExpect(jsonPath("$.pages").exists());
        result.andExpect(jsonPath("$.isbn").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/books/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void saveShouldReturnCreated() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(bookDTO);

        ResultActions result = mockMvc.perform(post("/books")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.title").exists());
        result.andExpect(jsonPath("$.author").exists());
        result.andExpect(jsonPath("$.pages").exists());
        result.andExpect(jsonPath("$.isbn").exists());
        result.andExpect(jsonPath("$.price").exists());
        result.andExpect(jsonPath("$.imgUrl").exists());
    }

}
