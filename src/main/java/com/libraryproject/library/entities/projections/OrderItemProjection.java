package com.libraryproject.library.entities.projections;


public interface OrderItemProjection {
    Double getPrice();
    Integer getQuantity();
    Long getOrderId();
    String getTitle();
    String getAuthor();
    Long getBookId();
}