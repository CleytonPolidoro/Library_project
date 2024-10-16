package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
