package com.example.reborn.type.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "receipt")
@NoArgsConstructor
@Getter
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long receiptId;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressDetail;

    @Column(nullable = false)
    private Long zipCode;
//수거날짜

    @Column(nullable = false)
    private String date;
    @Column(nullable = true)
    private String gatePassword;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String phoneNumber;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public Receipt(String address,String addressDetail,Long zipCode, String date, String gatePassword, String name, String phoneNumber, User user, LocalDateTime createdAt){
        this.address = address;
        this.addressDetail=addressDetail;
        this.zipCode=zipCode;
        this.date = date;
        this.gatePassword = gatePassword;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.createdAt = createdAt;
    }


}
