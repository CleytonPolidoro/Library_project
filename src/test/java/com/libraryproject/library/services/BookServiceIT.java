package com.libraryproject.library.services;

import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.entities.dto.BookMinDTO;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class BookServiceIT {

    @Autowired
    private BookService service;

    @Autowired
    private BookRepository repository;

    private Pageable pageable;
    private String existingAuthor, nonExistingAuthor, existingTitle;
    private BookDTO bookDTO, bookInsert;
    private Long existingId, nonExistignId;

    @BeforeEach
    void setUp() throws Exception{
        pageable = PageRequest.of(0, 10);
        bookDTO = Factory.createExistingBookDto();
        bookInsert = Factory.createBookDtoInsert();
        existingAuthor = bookDTO.getAuthor();
        existingTitle = bookDTO.getTitle();
        nonExistingAuthor = "Dotoiesvski";
        existingId = 1L;
        nonExistignId = 100L;
    }


    @Test
    public void findAllShouldReturnPageWhenSearchDataExists(){
        Page<BookMinDTO> result = service.findAll(pageable, existingTitle, existingAuthor);

        Assertions.assertTrue(result.hasContent());

    }

    @Test
    public void findAllShouldReturnEmptyPageWhenSearchDataDoesNotExist(){
        Page<BookMinDTO> result = service.findAll(pageable, existingTitle, nonExistingAuthor);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void findByIdShouldReturnBookWhenIdExists(){
        BookDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistignId);
        });
    }

    @Test
    public void insertShouldInsertObjectAndAutoIncrementWhenIdIsNull(){
        BookDTO result = service.insert(bookInsert);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), repository.count());

    }

    @Test
    public void insertShouldThrowUnprocessableExceptionWhenBookAlreadyExists(){
        Assertions.assertThrows(UnprocessableException.class, () -> {
            service.insert(bookDTO);
        });
    }
}
