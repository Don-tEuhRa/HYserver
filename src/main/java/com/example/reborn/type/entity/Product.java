package com.example.reborn.type.entity;

import javax.persistence.*;

import com.example.reborn.type.etc.CategoryName;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId; // 상품 아이디

    @Column(nullable = false)
    private String title; // 상품 명


    @Column(nullable = false)
    private Long price; // 상품 가격

    @Column(columnDefinition = "TEXT",nullable=true)
    private String content; // 상품 설명

    @Column(columnDefinition = "TEXT")
    private String thumbnailUrl; // 상품 썸네일 url

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt; // 상품 작성 시각

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt; // 상품 수정 시각

    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User seller; // 판매자

    @Enumerated(EnumType.STRING)
    private CategoryName categoryName; // 카테고리

    @Column(nullable = false)
    private Boolean isSold;
    @Builder
    public Product(String title, Long price, String content, String thumbnailUrl, LocalDateTime createdAt, LocalDateTime updatedAt, User seller, CategoryName categoryName,Boolean isSold){
        this.title = title;
        this.price = price;
        this.content = content;
        this.thumbnailUrl = thumbnailUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.seller = seller;
        this.categoryName = categoryName;
        this.isSold=isSold;
    }
}