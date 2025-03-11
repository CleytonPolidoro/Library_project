package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.User;
import com.libraryproject.library.entities.enums.OrderStatus;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderDTO {
    private Long id;
    private Long clientId;
    private Instant moment;
    private Integer status;

    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Long clientId, Instant moment, Integer status, OrderItemProjection items) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.clientId = clientId;
        this.items.add(new OrderItemDTO(items));
    }
    public OrderDTO(Long id, Long clientId, Instant moment, Integer status, Set <OrderItem> items) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.clientId = clientId;
        this.items = items.stream().map(x -> new OrderItemDTO(x)).toList();
    }

    public OrderDTO(OrderProjection proj){
        id = proj.getId();
        moment = proj.getMoment();
        clientId = proj.getClientId();
        status = proj.getStatus();
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus().getCode();
        items = entity.getItems().stream().map(x-> new OrderItemDTO(x)).toList();
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

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Long getClientId() {
        return clientId;
    }

    //
//    public UserDTO getUser() {
//        return user;
//    }
}
