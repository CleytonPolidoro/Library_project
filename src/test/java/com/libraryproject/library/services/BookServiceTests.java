package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.entities.dto.BookMinDTO;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.services.exceptions.ResourceNotFoundException;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import com.libraryproject.library.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class BookServiceTests {

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository; 

    private Long existId;
    private Long nonExistingId;
    private String existAuthor;
    private String nonExistAuthor;
    private String existTitle;
    private String nonExistTitle;
    private PageImpl page;
    private Page<Book> pageEmpty;
    private Book book;
    BookDTO validBookDTO;
    BookDTO invalidBookDTO;
    Book existingBook;
    BookDTO existingBookDto;
    Pageable pageable;
    @BeforeEach
    void setUp()throws Exception{
        existId = 1l;
        nonExistingId = 100l;
        existAuthor = "Ryan Holiday";
        nonExistAuthor = "igor";
        existTitle = "Diário estoico";
        nonExistTitle = "asas";
        book = Factory.createBook();
        validBookDTO = Factory.createBookDto();
        invalidBookDTO = Factory.createInvalidBookDto();
        existingBook = Factory.createExistingBook();
        existingBookDto = Factory.createExistingBookDto();
        page = new PageImpl<>(List.of(book));
        pageEmpty = new PageImpl<>(List.of());
        pageable = PageRequest.of(0, 10);

        Mockito.when(repository.searchAll(pageable, existTitle, existAuthor)).thenReturn(page);
        Mockito.when(repository.searchAll(pageable, nonExistTitle, existAuthor)).thenReturn(pageEmpty);
        Mockito.when(repository.searchAll(pageable, existTitle, nonExistAuthor)).thenReturn(pageEmpty);
        Mockito.when(repository.findById(existId)).thenReturn(Optional.of(book));
        Mockito.when(repository.save(book)).thenReturn(book);
        Mockito.when(repository.findAll()).thenReturn(List.of(existingBook));
    }

    @Test
    public void findAllShouldReturnPageWhenTitleOrAuthorExists(){
        Page<BookMinDTO> result = service.findAll(pageable,
                existTitle, existAuthor);

        Assertions.assertTrue(result.hasContent());

        Mockito.verify(repository).searchAll(pageable, existTitle, existAuthor);
    }

    @Test
    public void findAllShouldReturnPageWhenTitleDoesNotExistAndAuthorExists(){
        Page<BookMinDTO> result = service.findAll(pageable, nonExistTitle, existAuthor);

        Assertions.assertTrue(result.isEmpty());

        Mockito.verify(repository).searchAll(pageable, nonExistTitle, existAuthor);

    }

    @Test
    public void findAllShouldReturnPageWhenTitleExistAndAuthorDoesNotExist(){
        Page<BookMinDTO> result = service.findAll(pageable, existTitle, nonExistAuthor);

        Assertions.assertTrue(result.isEmpty());

        Mockito.verify(repository).searchAll(pageable, existTitle, nonExistAuthor);
    }

    @Test
    public void findByIdShouldReturnBookWhenIdExists(){
        BookDTO result = service.findById(existId);

        Assertions.assertNotNull(result);

        Mockito.verify(repository).findById(existId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnBookDtoWhenValidData(){
        BookDTO result = service.insert(validBookDTO);

        Assertions.assertNotNull(result);
        Mockito.verify(repository).save(book);
    }

    @Test void insertShouldThrowUnprocessableExceptionWhenBookExist(){
            Assertions.assertThrows(UnprocessableException.class, () -> {
            service.insert(existingBookDto);
        });
    }

}