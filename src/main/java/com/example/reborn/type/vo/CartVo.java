package com.example.reborn.type.vo;


import com.example.reborn.type.entity.CartProductRelation;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class CartVo {
    private Long userId;
    private Long productId;
    private Long price;
    private String title;
    private String thumnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public CartVo(CartProductRelation cart)
    {  this.productId=cart.getProduct().getProductId();
        this.userId = cart.getCart().getCartId();
        this.price = cart.getProduct().getPrice();
        this.title = cart.getProduct().getTitle();
        this.thumnailUrl = cart.getProduct().getThumbnailUrl();
        this.createdAt = cart.getCart().getCreatedAt();
        this.updatedAt = cart.getCart().getUpdatedAt();

    }
}
