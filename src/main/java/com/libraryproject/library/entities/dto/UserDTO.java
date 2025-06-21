package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;
    @NotBlank(message = "Campo requerido")
    @Size(min = 3, message = "Mínimo de 3 letras")
    private String name;

    @Email
    @NotBlank(message = "Campo requerido")
    private String email;
    private String phone;
    public UserDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public UserDTO(){}

    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank(message = "Campo requerido") @Size(min = 3, message = "Mínimo de 3 letras") String name) {
        this.name = name;
    }

    public void setEmail(@Email @NotBlank(message = "Campo requerido") String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
