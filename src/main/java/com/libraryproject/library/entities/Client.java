package com.libraryproject.library.entities;


public class Client extends User{
    private Double fines= 0.0;

    public Client(Long id, String name, String email, String password, String phone) {
        super(id, name, email, password, phone, "client");
    }

    public Client() {
    }

    public Double getFines() {
        return fines;
    }

    public void setFines(Double fines) {
        this.fines = fines;
    }
}
