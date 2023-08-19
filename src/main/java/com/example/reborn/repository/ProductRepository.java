package com.example.reborn.repository;

import com.example.reborn.type.entity.Product;

import com.example.reborn.type.etc.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductId(Long productId);

    List<Product> findAllByCategoryName(CategoryName category);

    List<Product> findAllByTitleContaining(String title);

    List<Product> findAllByIsSold(boolean b);

    List<Product> findAllByCategoryNameAndIsSold(CategoryName category, boolean b);

    List<Product> findAllByTitleContainingAndIsSold(String keyword, boolean b);

    List<Product> findTop10ByOrderByProductIdDesc();
}
