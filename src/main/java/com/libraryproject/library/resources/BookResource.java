package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(Pageable pageable, @RequestParam(name = "title", defaultValue = "") String title){
        Page<BookDTO> list = service.findAll(pageable, title);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        BookDTO book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping(value = "/author")
    public ResponseEntity<Page<BookDTO>> findByAuthor(Pageable pageable, @RequestParam(name = "author", defaultValue = "") String author){
        Page<BookDTO> list = service.findByAuthor(pageable, author);
        return ResponseEntity.ok().body(list);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookDTO> save(@Valid @RequestBody BookDTO book){
        BookDTO dto = service.insert(book);
        return ResponseEntity.ok().body(dto);
    }

}
