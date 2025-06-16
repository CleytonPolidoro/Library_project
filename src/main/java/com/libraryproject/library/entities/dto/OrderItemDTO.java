package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.projections.OrderItemProjection;

public class OrderItemDTO {
    private Integer quantity;
    private String title;
    private String author;
    private Double price;
    private Long bookId;

    public OrderItemDTO(){}

    public OrderItemDTO(Integer quantity, Long bookId, String bookTitle, String author, Double price) {
        this.quantity = quantity;
        this.title = bookTitle;
        this.author = author;
        this.price = price;
        this.bookId = bookId;
    }

    public OrderItemDTO(OrderItemProjection projection) {
        quantity = projection.getQuantity();
        price = projection.getPrice();
        title = projection.getTitle();
        author = projection.getAuthor();
        bookId = projection.getBookId();
    }

    public OrderItemDTO(OrderItem entity) {
        title = entity.getId().getBook().getTitle();
        author = entity.getId().getBook().getAuthor();
        quantity = entity.getQuantity();
        price = entity.getPrice();
        bookId = entity.getBook().getId();
    }


    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
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

}
