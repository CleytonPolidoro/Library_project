package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.User;

public class ClientDTO {
    private Long id;
    private String name;

    public ClientDTO() {
    }

    public ClientDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
