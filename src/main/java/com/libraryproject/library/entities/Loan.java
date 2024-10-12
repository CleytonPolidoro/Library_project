package com.libraryproject.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libraryproject.library.entities.enums.LoanStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;


@Entity
@Table(name = "tb_loans")
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;
    private Instant returnDay;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    public Loan(){}

    public Loan(Long id, Instant moment, Instant returnDay, User client) {
        this.id = id;
        this.moment = moment;
        this.returnDay = returnDay;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Instant getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(Instant returnDay) {
        this.returnDay = returnDay;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}