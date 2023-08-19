package com.example.reborn.repository;

import com.example.reborn.type.entity.Cart;

import com.example.reborn.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
 

    List<Cart> findAllByUser(User user);
    Optional<Cart> findByUser(User user);

    Optional<Cart> findByCartId(Long cartId);


}
