package com.example.reborn.type.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "product_Receipt_Relation")
@NoArgsConstructor
@Getter
@Setter
public class ProductReceiptRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long relationId;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "receipt_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Receipt receipt;

    @Builder
    public ProductReceiptRelation(Product product, Receipt receipt) {
        this.product = product;
        this.receipt = receipt;
    }
}
