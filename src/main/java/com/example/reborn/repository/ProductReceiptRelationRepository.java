package com.example.reborn.repository;

import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.ProductReceiptRelation;
import com.example.reborn.type.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductReceiptRelationRepository extends JpaRepository<ProductReceiptRelation,Long> {
    Optional<ProductReceiptRelation> findByReceipt(Receipt receipt);

    Optional<ProductReceiptRelation> findByProduct(Product product);
}
