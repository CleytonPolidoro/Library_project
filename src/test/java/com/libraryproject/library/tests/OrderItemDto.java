package com.libraryproject.library.tests;

import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.projections.OrderItemProjection;

public class OrderItemDto implements OrderItemProjection {
    private Double price;
    private Integer quantity;
    private Long orderId;
    private String title;
    private String author;
    private Long bookId;

    public OrderItemDto() {
    }

    public OrderItemDto(OrderItem entity) {
        price = entity.getPrice();
        quantity = entity.getQuantity();
        orderId = entity.getOrder().getId();
        title = entity.getBook().getTitle();
        author = entity.getBook().getAuthor();
        bookId = entity.getBook().getId();
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public Long getOrderId() {
        return orderId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public Long getBookId() {
        return bookId;
    }
}
