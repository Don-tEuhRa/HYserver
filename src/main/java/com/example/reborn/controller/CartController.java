package com.example.reborn.controller;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.CartService;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

    private final UserRepository userRepository;

    private final CartService cartService;
    @Operation(summary="장바구니에 제품 추가" ,description="추가할 상품의 상품 번호 Long productId")
    @PostMapping(value="/create/{productId}")
    public synchronized ResponseModel createCart(@PathVariable Long productId) {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        cartService.createCart(productId,user);

        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;
    }


    @Operation(summary="장바구니 물품 선택 삭제",description = "Long productId")
    @DeleteMapping(value="/delete/{productId}")
    public synchronized ResponseModel deleteSelectCart(@PathVariable Long productId) {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        cartService.deleteSelectCart(productId,user);

        return ResponseModel.builder().build();
    }


    @Operation(summary="장바구니 조회",description = "Long userId\n" +
            "    Long productId;\n" +
            "    Long price\n" +
            "    String title\n" +
            "    String thumnailUrl;\n")
    @GetMapping(value="/findAll")
    public ResponseModel findAllCart() {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("cartList",cartService.findAllCart(user));
        return responseModel;
    }
}
