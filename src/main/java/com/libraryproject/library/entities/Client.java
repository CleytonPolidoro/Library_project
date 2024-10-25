package com.libraryproject.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {

    @OneToMany(mappedBy = "client")
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public Client(Long id, String name, String email, String password, String phone) {
        super(id, name, email, password, phone);
    }

    public Client() {
    }


    public List<Order> getOrders() {

        return orders;
    }


}