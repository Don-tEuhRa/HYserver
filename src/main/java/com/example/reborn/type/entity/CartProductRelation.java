package com.example.reborn.type.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "cart_product_relation")
@NoArgsConstructor
@Getter
@Setter
public class CartProductRelation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long relationId;

    @ManyToOne
    @JoinColumn(name = "cart_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;






    @Builder
    public CartProductRelation(Cart cart, Product product)
    {
        this.cart = cart;
        this.product = product;


    }

}
