package com.example.reborn.service;

import com.example.reborn.repository.ProductImageRepository;
import com.example.reborn.repository.ProductRepository;
import com.example.reborn.type.entity.Product;
import com.example.reborn.type.entity.ProductImage;
import com.example.reborn.type.vo.ImageVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

public List<ImageVo> getProductImageList(Long productId){

    Product product=EntityUtil.findProductId(productRepository,productId);
    List<ProductImage> productImageList=productImageRepository.findAllByProduct(product);
    List<ImageVo> imageVoList= new ArrayList<>();
    if(!(productImageList.isEmpty())){
        for(ProductImage productImage:productImageList){
            ImageVo imageVo=new ImageVo(productImage);
            imageVoList.add(imageVo);
        }
        return imageVoList;
    }

    return null;
}

}
