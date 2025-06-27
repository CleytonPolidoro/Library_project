package com.libraryproject.library.tests;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.projections.OrderProjection;

import java.time.Instant;

public class OrderDto implements OrderProjection {
    private Long id;
    private Long clientId;
    private Integer status;
    private Instant moment;


    public OrderDto() {
    }

    public OrderDto(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus().getCode();
        clientId = entity.getClient().getId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getClientId() {
        return clientId;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public Instant getMoment() {
        return moment;
    }
}
