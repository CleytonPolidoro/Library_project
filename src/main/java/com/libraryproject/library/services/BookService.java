package com.libraryproject.library.services;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.repositories.BookRepository;
import com.libraryproject.library.services.exceptions.UnprocessableException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public List<Book> findAll(){
        return repository.findAll();
    }

    public Book findById(Long id){
        return repository.findById(id).get();
    }

    public List<Book> findByAuthor(String author){
        return repository.findByAuthorContainingIgnoreCase(author);

    }

    public List<Book>findByGendersName( String gender){
        return repository.findByGendersNameContainingIgnoreCase(gender);
    }

    public List<Book> findByTitle(String title){
        return repository.findByTitleContainingIgnoreCase(title);
    }

    public Book save(Book book){

        boolean bookExist = repository.findAll().stream().anyMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle())
                && b.getAuthor().equalsIgnoreCase(book.getAuthor()));

        if(bookExist){
            throw new UnprocessableException("Book already exists");
        }
        return repository.save(book);
    }

}
