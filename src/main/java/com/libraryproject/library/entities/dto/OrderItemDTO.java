package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.pk.OrderItemPK;
import com.libraryproject.library.entities.projections.OrderItemProjection;

public class OrderItemDTO {
    private Integer quantity;
    private Double price;
    private String book;
    private String author;

    public OrderItemDTO(OrderItemProjection projection) {
        quantity = projection.getQuantity();
        price = projection.getPrice();
        book = projection.getTitle();
        author = projection.getAuthor();
    }

    public OrderItemDTO(OrderItem entity) {
        book = entity.getId().getBook().getTitle();
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

    public String getBook() {
        return book;
    }
    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {

        return String.format("book: " + book+ "\nauthor: " + author + "\nprice: " + price + "\nquantity: " + quantity);
    }
}
