package com.libraryproject.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libraryproject.library.entities.pk.LoanItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "Loan_item")
public class LoanItem {

    @EmbeddedId
    private LoanItemPK id = new LoanItemPK();
    private Integer quantity;
    private Double price;

    public LoanItem() {
    }

    public LoanItem(Loan loan, Book book, Integer quantity, Double price) {
        id.setBook(book);
        id.setLoan(loan);
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonIgnore
    public Loan getLoan(){
        return id.getLoan();
    }

    public void setLoan(Loan loan){
        id.setLoan(loan);
    }


    public Book getBook(){
        return id.getBook();
    }

    public void setBook(Book book){
        id.setBook(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanItem loanItem = (LoanItem) o;
        return Objects.equals(id, loanItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
