package com.example.reborn.repository;

import com.example.reborn.type.entity.Interest;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<Interest> findByProductAndUser(Product product, User user);

    List<Interest> findAllByUser(User user);

    Boolean existsInterestByUserAndProduct(User user, Product product);

}
