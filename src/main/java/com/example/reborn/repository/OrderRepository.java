package com.example.reborn.repository;

import com.example.reborn.type.entity.Order;

import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByOrderId(Long orderId);

    List<Order> findAllByUser(User user);

    Optional<Order> findByUser(User user);


    List<Order> findAllByUserAndStatus(User user, OrderStatus orderStatus);

    Long countByUserAndStatus(User user, OrderStatus orderStatus);
}
