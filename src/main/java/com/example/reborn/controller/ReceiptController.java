package com.example.reborn.controller;

import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.ReceiptService;
import com.example.reborn.type.dto.ReceiptDto;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    private final UserRepository userRepository;

    private final ReceiptService receiptService;

    @Operation(summary="의류 수거 신청 ", description = "Json{ " +
            "        String addressDetail 상세주소\n"  +
            "        Long zipCode 우편번호\n"+
            "        String address 주소\n" +
            "        String date 수거날짜\n" +
            "        String gatePassword 현관비밀번호\n" +
            "        String name 기부자 이름\n" +
            "        String phoneNumber 전화번호\n}")
    @PostMapping(value="/create")
    public synchronized ResponseModel CreateReceipt( @Valid @RequestBody ReceiptDto dto) {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        receiptService.createReceipt(dto,user);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }

    @Operation(summary="의류 수거 신청 취소" ,description = "Long receiptId 의류 수거 접수 번호")
    @DeleteMapping(value="/cancel/{receiptId}")
    public synchronized ResponseModel CancelReceipt( @PathVariable Long receiptId)
    {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        receiptService.cancelReceipt(receiptId,user);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }





}