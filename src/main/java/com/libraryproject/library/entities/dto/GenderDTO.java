package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Gender;

public class GenderDTO {
    private Long id;
    private String name;

    public GenderDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenderDTO(Gender entity){
        id=entity.getId();
        name=entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
