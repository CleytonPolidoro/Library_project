package com.libraryproject.library.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.repositories.BookRepository;
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
public class BookResourceIT {

    @Autowired
    private BookRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil token;
    public String existingTitle, nonExistingTitle, username, password, bearerToken;
    public Long existingId, nonExistingId;
    public BookDTO bookDTO;

    @BeforeEach
    void setUp() throws Exception{
        existingTitle = "A arte da guerra";
        nonExistingTitle = "Noites brancas";
        existingId = 1L;
        nonExistingId = repository.count() + 1;
        bookDTO = Factory.createBookDtoInsert();
        username = "admin@gmail.com";
        password = "123456";
        bearerToken = token.obtainAccessToken(mockMvc, username, password);
    }

    @Test
    public void findAllShouldReturnPageAll() throws Exception {
        ResultActions result = mockMvc.perform(get("/books")
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("pageable").exists());
        result.andExpect(jsonPath("empty").isNotEmpty());
    }

    @Test
    public void findAllShouldReturnPageWithResearchedObjectsWhenParamExists() throws Exception {
        ResultActions result = mockMvc.perform(get("/books")
                .param("title", existingTitle)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("pageable").exists());
        result.andExpect(jsonPath("$.content[0].title").value(existingTitle));
        result.andExpect(jsonPath("empty").isNotEmpty());
    }

    @Test
    public void findAllShouldReturnPageEmptyWhenParamsDoesNotExist() throws Exception{
        ResultActions result = mockMvc.perform(get("/books")
                .param("title", nonExistingTitle)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("empty").value(true));
    }

    @Test
    public void findByIdShouldReturnBookWhenIdExists() throws Exception{
        ResultActions result = mockMvc.perform(get("/books/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("id").value(existingId));
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions result = mockMvc.perform(get("/books/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isNotFound());
    }

    @Test
    public void saveShouldSaveAndPersistObjectWhenDataIsValid() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(bookDTO);
        long last = repository.count();

        ResultActions result = mockMvc.perform(post("/books")
                .header("Authorization", "Bearer " + bearerToken )
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("id").value(last+1));
        result.andExpect(jsonPath("title").value(bookDTO.getTitle()));
        result.andExpect(jsonPath("author").value(bookDTO.getAuthor()));
        result.andExpect(jsonPath("price").value(bookDTO.getPrice()));
        result.andExpect(jsonPath("isbn").value(bookDTO.getIsbn()));
        result.andExpect(jsonPath("pages").value(bookDTO.getPages()));
    }

    @Test
    public void saveShouldReturnUnprocessableWhenDataDoesNotValid() throws Exception {
        bookDTO.getGenders().clear();
        String jsonBody = objectMapper.writeValueAsString(bookDTO);

        ResultActions result = mockMvc.perform(post("/books")
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isUnprocessableEntity());
    }
}
