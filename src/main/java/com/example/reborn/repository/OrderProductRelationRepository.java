package com.example.reborn.repository;

import com.example.reborn.type.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderProductRelationRepository extends JpaRepository<OrderProductRelation, Long> {


    boolean existsByOrderAndProduct(Order order, Product product);

    void deleteByOrderAndProduct(Order order, Product product);


    List<OrderProductRelation> findAllByOrder(Order order);

    void deleteAllByOrder(Order order);

    Optional<OrderProductRelation> findByOrder(Order order);
}