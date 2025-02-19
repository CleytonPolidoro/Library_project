package com.libraryproject.library.resources;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.dto.BookDTO;
import com.libraryproject.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<BookDTO>> findAll(){
        List<BookDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Long id){
        BookDTO book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<BookDTO>> findByAuthor(@PathVariable String author){
        List<BookDTO> list = service.findByAuthor(author);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/gender/{gender}")
    public ResponseEntity<List<BookDTO>> findByGendersName(@PathVariable String gender){
        List<BookDTO> list = service.findByGendersName(gender);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<List<BookDTO>> findByTitle(@PathVariable String title){
        List<BookDTO> list = service.findByTitle(title);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookDTO> save(@RequestBody Book book){
        service.insert(book);
        BookDTO dto = new BookDTO(book);
        return ResponseEntity.ok().body(dto);
    }

}
