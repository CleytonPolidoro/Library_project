package com.libraryproject.library.entities.pk;

import com.libraryproject.library.entities.Book;
import com.libraryproject.library.entities.Loan;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LoanItemPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanItemPK that = (LoanItemPK) o;
        return Objects.equals(loan, that.loan) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loan, book);
    }
}
