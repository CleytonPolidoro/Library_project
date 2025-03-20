package com.libraryproject.library.entities.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @NotBlank(message = "Campo requerido")
        @Size(min = 3, max=20, message = "O nome precisa ter entre 3 e 20 caracteres")
        String name,

        @NotBlank(message = "Campo requerido")
        @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
        String password,


        @Email
        @NotBlank(message = "Campo requerido")
        String email,
        String phone) {

}
