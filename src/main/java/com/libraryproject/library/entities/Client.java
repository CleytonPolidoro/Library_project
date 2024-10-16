package com.libraryproject.library.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User{
    private Double fines= 0.0;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public Client(Long id, String name, String email, String password, String phone) {
        super(id, name, email, password, phone);
    }

    public Client() {
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(Double fines) {
        this.fines = fines;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
