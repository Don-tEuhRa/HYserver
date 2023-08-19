package com.example.reborn.type.dto;

import com.example.reborn.type.etc.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
public class OrderDto {

    private Long userId;
    private String productCode;
    private String productName;
    private String productSize;
    private Long price;
    private OrderStatus status;
    private String thumnailUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
