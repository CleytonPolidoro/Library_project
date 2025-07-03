package com.libraryproject.library.tests;


import com.libraryproject.library.entities.*;
import com.libraryproject.library.entities.dto.*;
import com.libraryproject.library.entities.enums.OrderStatus;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;

import java.time.Instant;
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
        return new Book(1L, "Diário estoico", "Ryan Holiday", 496, "978_6555605556L", 64.90, "", createGender());
    }

    public static Gender createGender() {
        return new Gender(1L, "Desenvolvimento pessoal");
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

    public static BookMinDTO createBookMinDTO(){
        return new BookMinDTO(createBook());
    }

    public static BookDTO createInvalidBookDto(){
        List<GenderDTO> genders = new ArrayList<>();
        genders.add(createGenderDTO());
        return new BookDTO(null, "Di", " ", null, "978_6555605556L", 64.90, "", genders);

    }

    public static Book createExistingBook() {
        Gender genders = createGender();
        return new Book(1L,"Box Duna: Primeira Trilogia", "Frank Herbert", 1480, "978_6586064414L", 299.90, "", genders);
    }

    public static BookDTO createExistingBookDto() {
        return new BookDTO(createExistingBook());
    }

    public static Order createOrder(){
        return new Order(1L, Instant.parse("2024-10-06T00:00:00Z"), createUser(), OrderStatus.WAITING_PAYMENT);
    }

    public static OrderProjection createOrderProjection(){
        return new OrderDto(createOrder());
    }

    public static OrderItem createOrderItem(){
        return new OrderItem(
                createOrder(), createBook(), 2, 24.90
        );
    }

    public static OrderItemProjection createOrderItemDto(){
        return new OrderItemDto(createOrderItem());
    }

    public static OrderDTO createOrderDTO(){
        OrderDTO dto = new OrderDTO(createOrder());
        dto.getItems().add(new OrderItemDTO(createOrderItem()));
        return dto;
    }

    public static OrderMinDTO createOrderMinDTO(){
        OrderMinDTO orderMin = new OrderMinDTO(createOrderProjection());
        orderMin.getItems().add(new OrderItemDTO(createOrderItemDto()));
        return orderMin;
    }

    public static Role createRole(){
        return new Role(Role.Values.ADMIN.name());
    }
}
