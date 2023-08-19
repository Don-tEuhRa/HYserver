package com.example.reborn.type.dto;

import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InterestDto {

    private Long interestId;
    private Product product;
    private User user;
    private LocalDateTime createdAt;


}
