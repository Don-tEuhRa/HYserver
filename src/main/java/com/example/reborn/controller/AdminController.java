package com.example.reborn.controller;


import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.AdminService;
import com.example.reborn.service.UserService;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.OrderStatus;
import com.example.reborn.type.etc.ReceiptStatus;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;

    private final UserRepository userRepository;
    @Operation(summary="주문 상태 변경", description="주문 상태 변경 : 주문대기, 주문완료, 배달중, 배달완료, 주문취소")
    @PutMapping(value="/update/order/{orderId}/{status}")
    public synchronized ResponseModel updateOrder(@PathVariable Long orderId, @PathVariable OrderStatus status) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        adminService.updateOrder(orderId,user,status);

        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;

    }
    @Operation(summary="의류 수거 신청 취소 - admin")
    @DeleteMapping(value="/cancel/receipt/{receiptId}")
    public synchronized ResponseModel CancelReceiptForAdmin( @PathVariable Long receiptId) throws PermissionDeniedException {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        adminService.cancelReceiptForAdmin(receiptId,user);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

    @Operation(summary="의류 수거 신청 상태 변경")
    @PutMapping(value="/update/receipt/{receiptId}/{statusName}")
    public synchronized ResponseModel UpdateReceipt(@PathVariable Long receiptId, @PathVariable ReceiptStatus statusName) throws PermissionDeniedException {   ReceiptStatus status=statusName;
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        adminService.updateReceipt(receiptId,status,user);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }




}
