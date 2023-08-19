package com.example.reborn.repository;

import com.example.reborn.type.entity.Cart;
import com.example.reborn.type.entity.CartProductRelation;
import com.example.reborn.type.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CartProductRelationRepository extends JpaRepository<CartProductRelation, Long> {


    boolean existsByCartAndProduct(Cart cart, Product product);

    @Transactional
    void deleteByCartAndProduct(Cart cart, Product product);


    List<CartProductRelation> findAllByCart(Cart cart);

    void deleteAllByCart(Cart cart);
}
