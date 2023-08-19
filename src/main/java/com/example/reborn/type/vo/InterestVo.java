package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Interest;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class InterestVo {

    private Long interestId;
    private Long productId;
    private Product product;
    private User user;
    private LocalDateTime createdAt;

    public InterestVo(Long interestId, Product product, User user, LocalDateTime createdAt){
        this.interestId = interestId;
        this.productId = product.getProductId();
        this.product = product;
        this.user = user;
        this.createdAt = createdAt;
    }

    public InterestVo(Interest interest){
        this.interestId = interest.getInterestId();
        this.productId = interest.getProduct().getProductId();
        this.product = interest.getProduct();
        this.user = interest.getUser();
        this.createdAt = interest.getCreatedAt();
    }
}
