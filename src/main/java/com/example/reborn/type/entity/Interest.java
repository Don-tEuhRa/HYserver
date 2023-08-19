package com.example.reborn.type.entity;

import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "interest")
@NoArgsConstructor
@Getter
public class Interest {

    /*
    상품 찜 기능 요구사항

    로그인한 회원만 찜 가능 : 비로그인 시 로그인 페이지로 이동 조치
    상품 상세 페이지에서 찜하기 버튼 클릭 시 찜 목록에 추가
    찜 목록에서 상품 삭제 가능
    찜 목록에서 상품 상세 페이지로 이동 가능
    찜 상태에서 다시 버튼을 누르면 찜 초기화
    Role_User인 경우에만 찜 가능
    그 이외의 경우 찜 불가
     */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long interestId;

    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Interest(Product product, User user, LocalDateTime createdAt){
        this.product = product;
        this.user = user;
        this.createdAt = createdAt;
    }
}
