package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Transactional(readOnly = true)
    public List<BookDTO> findAll(){
        List<Book> result = repository.findAll();
        return result.stream().map(x -> new BookDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public BookDTO findById(Long id){
        Book result = repository.findById(id).get();
        return new BookDTO(result);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findByAuthor(String author){

        List<Book> result = repository.findByAuthorContainingIgnoreCase(author);
        return result.stream().map(x -> new BookDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDTO>findByGendersName( String gender){
        List<Book> result = repository.findByGendersNameContainingIgnoreCase(gender);

        return result.stream().map(x -> new BookDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public List<BookDTO> findByTitle(String title){
        List<Book> result = repository.findByTitleContainingIgnoreCase(title);

        return result.stream().map(x -> new BookDTO(x)).toList();
    }

    @Transactional
    public Book insert(Book book){

        boolean bookExist = repository.findAll().stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle())
                && b.getAuthor().equalsIgnoreCase(book.getAuthor()));

        if(bookExist){
            throw new UnprocessableException("Book already exists");
        }
        return repository.save(book);
    }

}
