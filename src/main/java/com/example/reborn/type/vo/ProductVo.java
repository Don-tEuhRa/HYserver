package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Product;
import com.example.reborn.type.etc.CategoryName;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class ProductVo {
    private Long productId;
    private String title;
    private Long price;
    private String content;
    private String thumbnailUrl;

    private CategoryName categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<String> imageUrl;

    private Boolean isInterested;

    private Boolean isSold;

    public ProductVo(Product product,Boolean isInterested,List<String> imageUrl)
    {
        this.productId=product.getProductId();
        this.content=product.getContent();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.categoryName = product.getCategoryName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.isInterested=isInterested;
        this.imageUrl=imageUrl;
        this.isSold=product.getIsSold();

    }


    public ProductVo(Product product,Boolean isInterested)
    {
        this.productId=product.getProductId();
        this.content=product.getContent();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.thumbnailUrl = product.getThumbnailUrl();
        this.categoryName = product.getCategoryName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.isInterested=isInterested;
        this.isSold=product.getIsSold();

    }


    public ProductVo(Long productId,String title,String content, Long price, String thumnailUrl,  CategoryName categoryName, LocalDateTime createdAt, LocalDateTime updatedAt,Boolean isInterested,List<String> imageUrl,Boolean isSold) {
        this.productId=productId;
        this.content=content;
        this.title = title;
        this.price = price;
        this.thumbnailUrl = thumnailUrl;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isInterested = isInterested;
        this.imageUrl=imageUrl;
        this.isSold=isSold;
    }

}
