package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;

    private ClientDTO client;
    private PaymentDTO payment;

    @NotEmpty(message = "Deve ter no mínimo um item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(){}

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }


    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client = new ClientDTO(entity.getClient());
        payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
        entity.getItems().forEach(item -> items.add(new OrderItemDTO(item)));
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setStatus(OrderStatus status) {
        if(status != null){
            this.status = status;
        };
    }

    public Double getTotal(){
        double sum = 0.0;
        for(OrderItemDTO item : items){
            sum += item.getSubTotal();
        }
        return sum;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
