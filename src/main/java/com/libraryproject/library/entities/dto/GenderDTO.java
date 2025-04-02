package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class GenderDTO {
    private Long id;
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, message = "Nome do genêro tem que ter no mínimo 3 caracteres")
    private String name;

    List<BookMinDTO> book;

    public GenderDTO(Long id, String name, List<BookMinDTO> books) {
        this.id = id;
        this.name = name;
        this.book = books;
    }

    public GenderDTO(Gender entity){
        id=entity.getId();
        name=entity.getName();
        book=entity.getBook().stream().map(x -> new BookMinDTO(x)).toList();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<BookMinDTO> getBook() {
        return book;
    }
}
