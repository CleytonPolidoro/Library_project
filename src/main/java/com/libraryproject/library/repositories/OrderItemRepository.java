package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
