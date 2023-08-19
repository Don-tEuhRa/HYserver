package com.example.reborn.type.dto;

import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.CategoryName;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductDto {

    private long productId;
    private String title; // 상품 명
    private Long price; // 상품 가격
    private String content; // 상품 설명
    private String thumbnailUrl; // 상품 썸네일 url
    private LocalDateTime createdAt; // 상품 작성 시각
    private LocalDateTime updatedAt; // 상품 수정 시각
    private User seller; // 판매자
    private CategoryName category; // 카테고리
    private Boolean isLiked; // 좋아요 여부
    private Boolean isSold;

}
