package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.entities.dto.BookMinDTO;
import com.libraryproject.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<Page<BookMinDTO>> findAll(Pageable pageable, @RequestParam(name = "title", defaultValue = "") String title,
                                                    @RequestParam(name = "author", defaultValue = "") String author){
        Page<BookMinDTO> list = service.findAll(pageable, title, author);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        BookDTO book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookDTO> save(@Valid @RequestBody BookDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
