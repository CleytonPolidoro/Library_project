package com.libraryproject.library.repositories;

import com.libraryproject.library.entities.Order;
import com.libraryproject.library.entities.OrderItem;
import com.libraryproject.library.entities.dto.OrderDTO;
import com.libraryproject.library.entities.projections.OrderItemProjection;
import com.libraryproject.library.entities.projections.OrderProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT orders.status, orders.client_id, orders.id, orders.moment " +
            "FROM orders")
    Page<OrderProjection> searchAll(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT order_item.order_id, order_item.price, order_item.quantity, Books.id, Books.author " +
            "FROM order_item " +
            "INNER JOIN Books ON Books.id = order_item.book_id " +
            "WHERE order_item.order_id IN :list")
    List<OrderItemProjection> findOrdersAndItems(List<Integer> list);

    @Query(nativeQuery = true, value =
            "SELECT orders.status, orders.client_id, orders.id, orders.moment " +
            "FROM orders " +
            "WHERE orders.id = :id"
    )
    Optional <OrderProjection> searchById(Long id);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT orders.status, orders.client_id, orders.id, orders.moment " +
            "FROM orders " +
            "WHERE orders.moment BETWEEN :minDate AND :maxDate")
//        "SELECT order_item.order_id, order_item.price, order_item.quantity, Books.title, Books.author, "+
//        "orders.status, orders.client_id, orders.id, orders.moment "+
//        "FROM order_item "+
//        "INNER JOIN Books ON Books.id = order_item.book_id "+
//        "INNER JOIN Orders ON Orders.id = order_item.order_id " +
//        "WHERE orders.moment BETWEEN :minDate AND :maxDate")
    Page<OrderProjection>findOrdersBetweenDates(Instant minDate, Instant maxDate, Pageable pageable);


    List<Order> findOrdersByClientId(Long id);
}