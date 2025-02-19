package com.libraryproject.library.services;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<Order> orders = repository.findAll();
        return orders.stream().map(x -> new OrderDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).get();
        return new OrderDTO(order);
    }

}
