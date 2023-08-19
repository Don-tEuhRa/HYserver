package com.example.reborn.type.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "order_product_relatio")
@NoArgsConstructor
@Getter
@Setter
public class OrderProductRelation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long relationId;

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;



    @Builder
    public OrderProductRelation(Order order, Product product)
    {
        this.order = order;
        this.product = product;


    }

}