package com.example.reborn.controller;


import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.FileUploadService;
import com.example.reborn.service.ProductImageService;
import com.example.reborn.service.ProductService;
import com.example.reborn.type.dto.ProductCreateDto;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.CategoryName;
import com.example.reborn.type.vo.*;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    private final UserRepository userRepository;

    private final ProductImageService productImageService;



    // 상품 게시글 저장 post 방식
    @Operation(summary = "상품 게시글 저장" ,description="\n" +
            "        Long price 가격 \n" +
            "        String title 상품 이름  \n" +
            "        CategoryName categoryName 카테고리 이름 \n" +
            "        List<MultipartFile> files 멀티 파일 \n" +
            "        Long receiptId(default=1) 접수번호")
    @PostMapping("/create/file")
    public synchronized ResponseModel productSaveFile(ProductFileVo dto) throws FileUploadFailException, PermissionDeniedException {

        //Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,2L);
        productService.saveProduct(user,dto);

        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;
    }

    @Operation(summary = "상품 게시글 저장",description="\n" +
            "        Long price 가격 \n" +
            "        String title 상품 이름  \n" +
            "        CategoryName categoryName 카테고리 이름 \n" +
            "        Long receiptId(default=1) 접수번호")
    @PostMapping("/create/test")
    public synchronized ResponseModel productSave(ProductCreateDto dto){

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        productService.saveProductTest(user,dto);
        return responseModel;
    }

    @Operation(summary = "상품-이미지 삽입",description = "List<String> imgUrl \n"+
    "Long productId")
    @PostMapping("/insert/img/{postId}")
    public synchronized ResponseModel insertImage( @Valid @RequestBody List<String> imgUrl,@PathVariable Long postId)
    {
        ResponseModel responseModel = ResponseModel.builder().build();
        productService.insertImgUrl(imgUrl,postId);
        return responseModel;
    }

    @Operation(summary = "상품 설명 긴 이미지 설정",description = "String imgUrl , Long postId")
    @PutMapping("/insert/img/content/{postId}")
    public synchronized ResponseModel contentImage(@Valid  @RequestBody String imgUrl, @PathVariable Long postId)
    {
        ResponseModel responseModel = ResponseModel.builder().build();
        productService.contentImgUrl(imgUrl,postId);
        return responseModel;
    }


    // 상품 게시글 조회 get 방식 - 상세 보기
    @Operation(summary = "상품 조회", description = "Long productId\n" +
            "   String title\n" +
            "   Long price\n" +
            "   String content\n" +
            "   CategoryName categoryName\n" +
            "   List<String> imageUrl\n" +
            "   Boolean isInterested\n" +
            "   Boolean isSold")
    @GetMapping("/show/{productId}")
    public ResponseModel showProduct(@PathVariable("productId") Long productId){
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        try {
            ProductVo product = productService.getProduct(productId,user);
            List<ImageVo> productImageList = productImageService.getProductImageList(productId);
            responseModel.addData("product", product);
        }
        catch(EntityNotFoundException e){ // 상품이 존재하지 않을 때 로직
          throw new EntityNotFoundException("해당 상품이 존재하지 않습니다.");
        }
        // 상품 게시글로 이동
        return responseModel;
    }



    //상품 게시글 수정 put 방식
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @PutMapping("/{id}/edit")
    public synchronized ResponseModel editProduct(@PathVariable("id") Long productId,
                                    ProductFileVo updateProductDto) throws FileUploadFailException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        productService.updateProduct(updateProductDto,productId, user);

        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;
    }




    // 상품 게시글 삭제
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/{id}/delete")
    public synchronized ResponseModel deleteProduct(@PathVariable("id") Long productId){

        ResponseModel responseModel = ResponseModel.builder().build();
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        productService.deleteProduct(productId, user);

        return responseModel;
    }

    // 상품 카테고리별 조회
    @Operation(summary = "상품 카테고리별 조회", description = "Long productId\n" +
            "   String title\n" +
            "   Long price\n" +
            "   String content\n" +
            "   String thumbnailUrl\n" +
            "   CategoryName categoryName\n" +
            "   List<String> imageUrl\n" +
            "   Boolean isInterested\n" +
            "   Boolean isSold")
    @GetMapping("/category/{category}")
    public ResponseModel showProductByCategory(@PathVariable("category") CategoryName category) {

        ResponseModel responseModel = ResponseModel.builder().build();
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        List<ProductVo> products = productService.findProductsByCategory(category,user);
        responseModel.addData("products", products);

        return responseModel;
    }


    // 모든 상품 게시글 조회
    @Operation(summary = "상품 전체 조회", description = "Long productId\n" +
            "   String title\n" +
            "   Long price\n" +
            "   String content\n" +
            "   String thumbnailUrl\n" +
            "   CategoryName categoryName\n" +
            "   List<String> imageUrl\n" +
            "   Boolean isInterested\n" +
            "   Boolean isSold")
    @GetMapping("/list")
    @Transactional(readOnly = true)
    public ResponseModel showListProducts() {

        ResponseModel responseModel = ResponseModel.builder().build();
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        List<ProductVo> products = productService.findAllProducts(user);
        responseModel.addData("products", products);
        return responseModel;
    }

    @Operation(summary = "상품 전체 조회", description = "Long productId\n" +
            "   String title\n" +
            "   Long price\n" +
            "   String content\n" +
            "   String thumbnailUrl\n" +
            "   CategoryName categoryName\n" +
            "   List<String> imageUrl\n" +
            "   Boolean isInterested\n" +
            "   Boolean isSold")
    @GetMapping("/list/UnSold")
    @Transactional(readOnly = true)
    public ResponseModel showListUnsoldProducts() {

        ResponseModel responseModel = ResponseModel.builder().build();
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);

        List<ProductVo> products = productService. findAllUnSoldProducts(user);
        responseModel.addData("products", products);
        return responseModel;
    }

    // 상품 검색
    @Operation(summary = "상품 검색", description = "Long productId\n" +
            "   String title\n" +
            "   Long price\n" +
            "   String content\n" +
            "   String thumbnailUrl\n" +
            "   CategoryName categoryName\n" +
            "   List<String> imageUrl\n" +
            "   Boolean isInterested\n" +
            "   Boolean isSold")
    @GetMapping("/search/{keyword}")
    public ResponseModel searchProducts(@PathVariable("keyword") String keyword) {

        ResponseModel responseModel = ResponseModel.builder().build();
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        List<ProductVo> products = productService.searchProducts(keyword,user);
        responseModel.addData("products", products);

        return responseModel;
    }


}
