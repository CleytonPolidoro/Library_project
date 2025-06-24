package com.libraryproject.library.tests;


import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Gender;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.dto.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

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

    public static Book createBook(){
        return new Book(null, "Diário estoico", "Ryan Holiday", 496, "978_6555605556L", 64.90, "", createGender());
    }

    public static Gender createGender() {
        return new Gender(null, "Desenvolvimento pessoal");
    }

    public static Gender createSaveGender() {
        return new Gender(null, "action");
    }

    public static GenderDTO createSaveGenderDto() {
        return new GenderDTO(createSaveGender());
    }

    public static GenderDTO createGenderDTO(){
        return new GenderDTO(createGender());
    }


    public static BookDTO createBookDto(){
        return new BookDTO(createBook());
    }

    public static BookDTO createInvalidBookDto(){
        List<GenderDTO> genders = new ArrayList<>();
        genders.add(createGenderDTO());
        return new BookDTO(null, "Di", " ", null, "978_6555605556L", 64.90, "", genders);

    }

    public static Book createExistingBook() {
        Gender genders = createGender();
        return new Book(null,"Box Duna: Primeira Trilogia", "Frank Herbert", 1480, "978_6586064414L", 299.90, "", genders);
    }

    public static BookDTO createExistingBookDto() {
        return new BookDTO(createExistingBook());
    }
}
