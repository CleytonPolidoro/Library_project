package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.enums.OrderStatus;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderMinDTO {

    private Long id;
    private Long clientId;
    private Instant moment;
    private OrderStatus status;

    List<OrderItemDTO> items = new ArrayList<>();

    public OrderMinDTO(Long id, Long clientId, Instant moment, Integer status, OrderItemProjection items) {
        this.id = id;
        this.moment = moment;
        this.status = OrderStatus.valueOf(status);
        this.clientId = clientId;
        this.items.add(new OrderItemDTO(items));
    }

    public OrderMinDTO(OrderProjection proj){
        id = proj.getId();
        moment = proj.getMoment();
        clientId = proj.getClientId();
        status = OrderStatus.valueOf(proj.getStatus());
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotal(){
        double sum = 0.0;
        for(OrderItemDTO item : items){
            sum += item.getSubTotal();
        }
        return sum;
    }
}
