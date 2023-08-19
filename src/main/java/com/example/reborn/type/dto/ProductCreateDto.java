package com.example.reborn.type.dto;

import com.example.reborn.type.etc.CategoryName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDto {
    private Long price;
    private String title;
    private CategoryName categoryName;
    private Long receiptId;
    private String content;
}

