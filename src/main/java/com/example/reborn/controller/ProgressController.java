package com.example.reborn.controller;

import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.ProgressService;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final UserRepository userRepository;

    private final ProgressService progressService;


    @Operation(summary = "진행 상황 표기",description = " Long donationStatusId;\n" +
            "    Long count;\n" +
            "    ReceiptStatus donationStatus;")
    @GetMapping(value="/findAll")
    public ResponseModel findAllDonationStatus() {
     Long userId= getAuthenticationInfoUserId();
     User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("donationStatusList",progressService.findAll(user));
        return responseModel;
    }


}
