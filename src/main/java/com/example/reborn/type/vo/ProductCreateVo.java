package com.example.reborn.type.vo;

import com.example.reborn.type.etc.CategoryName;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProductCreateVo {
  
    private Long price;
    private String title;
    private CategoryName categoryName;
    private Long receiptId;
}
