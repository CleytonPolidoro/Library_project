package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> findAll(){
        List<Book> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id){
        Book book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<Book>> findByAuthor(@PathVariable String author){
        List<Book> list = service.findByAuthor(author);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/gender/{gender}")
    public ResponseEntity<List<Book>> findByGendersName(@PathVariable String gender){
        List<Book> list = service.findByGendersName(gender);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<List<Book>> findByTitle(@PathVariable String title){
        List<Book> list = service.findByTitle(title);
        return ResponseEntity.ok().body(list);
    }
}
