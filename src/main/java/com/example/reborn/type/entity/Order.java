package com.example.reborn.type.entity;

import javax.persistence.*;

import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.PaymentMethod;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status")
@NoArgsConstructor
@Getter
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = true)
    private Long point;

    @CreatedDate
    @Column(updatable = false)
   private LocalDateTime created_at;

    @Builder
    public Order(User user, PaymentMethod paymentMethod, Long point, LocalDateTime created_at, OrderStatus status){
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.point = point;
        this.created_at = created_at;
        this.status = status;
    }


    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
