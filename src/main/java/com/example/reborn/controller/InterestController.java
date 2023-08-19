package com.example.reborn.controller;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.InterestService;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.vo.ProductVo;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/interest")
public class InterestController {

    private final InterestService interestService;

    private final UserRepository userRepository;

    @Operation(summary = "찜하기",description = "Long producId")
    @PostMapping(value = "/save/{productId}")
    public synchronized ResponseModel interestSave(@PathVariable Long productId) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        interestService.saveInterest(productId, user);
        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;
    }

    @Operation(summary = "찜하기 취소",description = "Long producId")
    @DeleteMapping(value = "/delete/{productId}")
    public synchronized ResponseModel interestDelete(@PathVariable Long productId) {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        interestService.deleteInterest(productId, user);
        ResponseModel responseModel = ResponseModel.builder().build();

        return responseModel;
    }


    @Operation(summary = "찜한 상품 리스트")
    @GetMapping("/list")
    public synchronized ResponseModel getInterestList() {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        List<ProductVo> productVoList = interestService.getInterestList(user);
        responseModel.addData("productVoList", productVoList);

        return responseModel;
    }

}
