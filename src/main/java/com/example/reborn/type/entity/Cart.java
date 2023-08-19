package com.example.reborn.type.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor
@Getter
public class Cart {
    //Table shopping_cart {
    //cart_id integer [primary key]
    //user_id integer [ref: > user.user_id]
    //product_id integer [ref: > product.product_id]
    //created_at date
    //update_at date
    //}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    @Builder
    public Cart(User user, LocalDateTime createdAt, LocalDateTime updatedAt,boolean isOrdered){
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }

    public void setUpdatedAt(LocalDateTime now) {
        this.updatedAt = now;
    }


}