package com.libraryproject.library.tests;


import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.UserDTO;
import com.libraryproject.library.entities.dto.UserInsertDTO;
import com.libraryproject.library.entities.dto.UserUpdateDTO;
import org.springframework.security.core.userdetails.UserDetails;

public class Factory {

    public static User createUser(){
        return new User(null,"Cleyton", "cleyton.polidoro@gmail.com", "123456", "2255555555");
    }

    public static UserDTO createUserDTO(){
        return new UserDTO(createUser());
    }

    public static UserInsertDTO createUserInsertDTO(){
        UserInsertDTO dto = new UserInsertDTO();
        dto.setName("ana");
        dto.setEmail("ana@gmail.com");
        dto.setPhone("2255555555");
        dto.setPassword("123456");

        return dto;
    }

    public static UserUpdateDTO createUserUpdateDTO(){
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setName("Cleyton");
        dto.setEmail("cleyton@gmail.com");
        dto.setPhone("2255555555");

        return dto;
    }

    public static UserDetailsDto createUserDetailsDto(){
        return new UserDetailsDto("cleyton@gmail.com", "123456", 1L, "SCOPE_CLIENT");
    }
}
