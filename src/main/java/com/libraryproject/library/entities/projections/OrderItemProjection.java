package com.libraryproject.library.entities.projections;

import java.time.Instant;
import java.util.Set;

public interface OrderItemProjection {
    Double getPrice();
    Integer getQuantity();
    Long getOrderId();
    Long getBookId();
    String getAuthor();
}