package com.libraryproject.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {


    public Client(Long id, String name, String email, String password, String phone) {
        super(id, name, email, password, phone);
    }

    public Client(Object o, String mariaBrown, String mail, String encode, String number, Role roleClient) {
    }

    public Client() {

    }


}