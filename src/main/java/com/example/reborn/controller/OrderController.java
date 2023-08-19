package com.example.reborn.controller;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.CartService;
import com.example.reborn.service.OrderService;
import com.example.reborn.type.dto.PaymentDto;
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
@RequestMapping("/order")
public class OrderController {

    private final UserRepository userRepository;

    private final OrderService orderService;

    private final CartService cartService;

    @Operation(summary="주문 조회")
    @GetMapping(value="/findAll")
    public ResponseModel findAllOrder() {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("orderList",orderService.findAllOrder(user));
        return responseModel;

    }

    @Operation(summary="결제 정보 저장")
    @PostMapping(value="/save")
    public synchronized ResponseModel saveOrder(
                                 @RequestBody PaymentDto dto) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        orderService.saveOrder(dto,user);
        for(Long productId:dto.getProductId())
        {
            cartService.deleteSelectCart(productId,user);
        }


        return ResponseModel.builder().build();

    }





}
