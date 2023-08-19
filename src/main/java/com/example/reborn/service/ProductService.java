package com.example.reborn.service;

import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.handler.FileHandler;
import com.example.reborn.repository.*;
import com.example.reborn.type.dto.ProductCreateDto;
import com.example.reborn.type.entity.*;
import com.example.reborn.type.etc.CategoryName;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.vo.ProductFileVo;
import com.example.reborn.type.vo.ProductVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FileHandler fileHandler;
    private final ProductImageRepository productImageRepository;

    private final ReceiptRepository receiptRepository;

    private final InterestRepository interestRepository;

    private final ProductReceiptRelationRepository productReceiptRelationRepository;

    private final DonationStatusRepository donationStatusRepository;

    private final SearchCountRepository searchCountRepository;

    private final SearchService searchService;




    public List<String> findImageUrl(Product product)
    {
        List<ProductImage> imageUrl=productImageRepository.findAllByProduct(product);
        List<String>imageList=new ArrayList<>();
        for(ProductImage imgList:imageUrl)
        {
            imageList.add(imgList.getImageUrl());

        }
        return imageList;
    }
    // create : 상품 게시글 등록

    public void saveProductTest(User user, ProductCreateDto productDto) {
        // user가 관리자인지 검사
        if(user.getRole() != Role.ADMIN){
            throw new IllegalArgumentException("관리자만 상품을 저장할 수 있습니다.");
        }

        Receipt  receipt=receiptRepository.findByReceiptId(productDto.getReceiptId())
                .orElseThrow(EntityNotFoundException::new);

        Product product = Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .categoryName(productDto.getCategoryName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .content(productDto.getContent())
                .seller(user)
                .isSold(false)
                .build();

        productRepository.save(product);

        ProductReceiptRelation productReceiptRelation = ProductReceiptRelation.builder()
                .product(product)
                .receipt(receipt)
                .build();
        productReceiptRelationRepository.save(productReceiptRelation);
    }




    public void saveProduct(User user, ProductFileVo productDto) throws FileUploadFailException, PermissionDeniedException {

        // user가 관리자인지 검사
        if(user.getRole() != Role.ADMIN){
            throw new PermissionDeniedException("관리자만 상품을 저장할 수 있습니다.");
        }

        Receipt  receipt=receiptRepository.findByReceiptId(productDto.getReceiptId())
                .orElseThrow(EntityNotFoundException::new);

        Product product = Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .categoryName(productDto.getCategoryName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .isSold(false)
                .seller(user)
                .build();

        productRepository.save(product);


        List<ProductImage> productImages = new ArrayList<>();

        if(!(productDto.getFiles().isEmpty())){
            for(MultipartFile file : productDto.getFiles()) {
                String ImgUrl = fileHandler.saveFile(file);
                ProductImage productImage = ProductImage.builder()
                        .product(product)
                        .imageUrl(ImgUrl)
                        .build();
                productImageRepository.save(productImage);
                productImages.add(productImage);
            }
            product.setThumbnailUrl(productImages.get(0).getImageUrl());
            product.setContent(productImages.get(productImages.size()-1).getImageUrl());
            productRepository.save(product);
        }
        ProductReceiptRelation productReceiptRelation = ProductReceiptRelation.builder()
                .product(product)
                .receipt(receipt)
                .build();

        productReceiptRelationRepository.save(productReceiptRelation);

        DonationStatus donationStatus=donationStatusRepository.findByReceipt(receipt).orElseThrow(EntityNotFoundException::new);
        donationStatus.setStatusName(ReceiptStatus.판매중);
        donationStatusRepository.save(donationStatus);
    }








    // read : 상품 게시글 조회

    public ProductVo getProduct(Long productId,User user){
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(EntityNotFoundException::new);
        Boolean isInterest = interestRepository.existsInterestByUserAndProduct(user,product);

        return new ProductVo(product,isInterest, findImageUrl(product));
    }


    // update : 상품 게시글 수정

    public Long updateProduct(ProductFileVo newProductData, Long productId,User user) throws FileUploadFailException {
        Product existingProduct = productRepository.findByProductId(productId)
                .orElseThrow(EntityNotFoundException::new);


        List<ProductImage> productImages = new ArrayList<>();

        if(!(newProductData.getFiles().isEmpty())){

            productImageRepository.deleteAllByProduct(existingProduct);
            for(MultipartFile file : newProductData.getFiles()) {
                String ImgUrl = fileHandler.saveFile(file);
                ProductImage productImage = ProductImage.builder()
                        .product(existingProduct)
                        .imageUrl(ImgUrl)
                        .build();
                productImageRepository.save(productImage);
                productImages.add(productImage);
            }

        }

        // 기존 상품 정보 업데이트
        existingProduct.setTitle(newProductData.getTitle());
        existingProduct.setPrice(newProductData.getPrice());
        existingProduct.setUpdatedAt(LocalDateTime.now());
        existingProduct.setThumbnailUrl(productImages.get(0).getImageUrl());

        // 상품 정보 저장
        productRepository.save(existingProduct);

        return productId;
    }


    public void insertImgUrl(List<String>imgUrl,Long productId)
    {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(EntityNotFoundException::new);

        for(String img:imgUrl)
        {
            ProductImage image= ProductImage.builder()
                    .imageUrl(img)
                    .product(product)
                    .build();
            productImageRepository.save(image);
        }
        product.setThumbnailUrl(imgUrl.get(0));
        productRepository.save(product);

    }

    // delete : 상품 게시글 삭제

    public void deleteProduct(Long productId, User user){

      /*  // user가 관리자인지 검사
        if(user.getRole() != Role.ADMIN){
            throw new IllegalArgumentException("관리자만 상품을 삭제할 수 있습니다.");
        }*/

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(EntityNotFoundException::new);
        productImageRepository.deleteAllByProduct(product);
        productRepository.delete(product);
    }


    public List<ProductVo> findProductsByCategory(CategoryName category, User user){
        List<Product> products = productRepository.findAllByCategoryNameAndIsSold(category,false);

        return products.stream()
                .map(product -> new ProductVo(product.getProductId(),product.getTitle(),product.getContent(), product.getPrice(), product.getThumbnailUrl(),
                        product.getCategoryName(), product.getCreatedAt(), product.getUpdatedAt(),
                        interestRepository.existsInterestByUserAndProduct(user, product),findImageUrl(product),product.getIsSold()
                ))
                .collect(Collectors.toList());
    }

    // read : 상품 게시글 리스팅

    public List<ProductVo> findAllProducts(User user){

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> new ProductVo(product,interestRepository.existsInterestByUserAndProduct(user,product)))
                .collect(Collectors.toList());
    }


    public List<ProductVo> findAllUnSoldProducts(User user){

        //List<Product> products = productRepository.findAllByIsSold(false);

        List<Product> products = productRepository.findTop10ByOrderByProductIdDesc();

        return products.stream()
                .map(product -> new ProductVo(product,interestRepository.existsInterestByUserAndProduct(user,product)))
                .collect(Collectors.toList());
    }






    public List<ProductVo> searchProducts(String keyword, User user) {
        List<Product> products = productRepository.findAllByTitleContainingAndIsSold(keyword,false);

        if(searchCountRepository.existsByKeyword(keyword))
        {
           SearchCount search= searchCountRepository.findByKeyword(keyword).orElseThrow();
           search.setCount(search.getCount()+1);
            searchCountRepository.save(search);

        }else{
            SearchCount searchCount=SearchCount.builder()
                    .keyword(keyword).
                    count(1L).build();
            searchCountRepository.save(searchCount);
            searchService.searchCount(keyword);
        }


;

        return products.stream()
                .map(product -> new ProductVo(product, interestRepository.existsInterestByUserAndProduct(user, product),findImageUrl(product)))
                .collect(Collectors.toList());
    }


    public void contentImgUrl(String imgUrl, Long productId) {
        Product product=EntityUtil.findProductId(productRepository,productId);
        product.setContent(imgUrl);
        productRepository.save(product);

    }
}

