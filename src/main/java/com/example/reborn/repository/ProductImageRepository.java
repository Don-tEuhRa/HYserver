package com.example.reborn.repository;

import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    List<ProductImage> findAllByProduct(Product product);

    void deleteAllByProduct(Product existingProduct);
}
