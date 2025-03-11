package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Gender;

import java.util.ArrayList;
import java.util.List;

public class GenderDTO {
    private Long id;
    private String name;

    List<BookDTO> book;

    public GenderDTO(Long id, String name, List<BookDTO> books) {
        this.id = id;
        this.name = name;
        this.book = books;
    }

    public GenderDTO(Gender entity){
        id=entity.getId();
        name=entity.getName();
        book=entity.getBook().stream().map(x -> new BookDTO(x)).toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookDTO> getBook() {
        return book;
    }
}
