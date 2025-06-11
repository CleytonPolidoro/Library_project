package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.projections.OrderItemProjection;

public class OrderItemDTO {
    private Integer quantity;
    private Long bookId;
    private String author;
    private Double price;

    public OrderItemDTO(Integer quantity, Long bookId, String author, Double price) {
        this.quantity = quantity;
        this.bookId = bookId;
        this.author = author;
        this.price = price;
    }

    public OrderItemDTO(OrderItemProjection projection) {
        quantity = projection.getQuantity();
        price = projection.getPrice();
        bookId = projection.getBookId();
        author = projection.getAuthor();
    }

    public OrderItemDTO(OrderItem entity) {
        bookId = entity.getId().getBook().getId();
        author = entity.getId().getBook().getAuthor();
        quantity = entity.getQuantity();
        price = entity.getPrice();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Long getBookId() {
        return bookId;
    }
    public String getAuthor() {
        return author;
    }

    public Double getSubTotal(){
        return price*quantity;
    }

    @Override
    public String toString() {

        return String.format("book: " + bookId+ "\nauthor: " + author + "\nprice: " + price + "\nquantity: " + quantity);
    }
}
