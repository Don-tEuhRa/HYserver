package com.example.reborn.type.vo;

import com.example.reborn.type.entity.ProductImage;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ImageVo {

    private String url;

    public ImageVo(ProductImage productImage) {
        url=productImage.getImageUrl();
    }



}
