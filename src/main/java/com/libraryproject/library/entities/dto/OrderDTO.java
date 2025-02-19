package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.enums.OrderStatus;

import java.time.Instant;

public class OrderDTO {
    private Long id;
    private Instant moment;
    private Integer status;

    public OrderDTO(Long id, Instant moment, OrderStatus status) {
        this.id = id;
        this.moment = moment;
        setStatus(status);
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus().getCode();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(status);
    }

    public void setStatus(OrderStatus status) {
        if(status != null){
            this.status = status.getCode();
        };
    }
}
