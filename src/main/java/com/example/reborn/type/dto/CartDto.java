package com.example.reborn.type.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartDto {

    private Long userId;
    private String productCode;
    private String productSize;
    private Long price;
    private Long discountPrice;
    private String title;
    private String thumnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
