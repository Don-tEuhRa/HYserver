package com.example.reborn.controller;

import com.example.reborn.exception.FileUploadFailException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.MypageService;
import com.example.reborn.type.dto.AddressDto;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.vo.MypageVo;
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
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    private final UserRepository userRepository;

    @Operation(summary = "마이페이지")
    @GetMapping(value = "/")
    public synchronized ResponseModel mypage(
    ) throws FileUploadFailException {
      Long userId= getAuthenticationInfoUserId();
      User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
       MypageVo vo= mypageService.mypage(user);
        responseModel.addData("vo",vo);

        return responseModel;
    }

    @Operation(summary = "주소,전화번호, 비밀번호 저장",description = "//  {" +
            "String name 이름\n"+
            "String address 주소\n" +
            "    String addressDetail 상세주소\n" +
            "    Long zipCode 우편번호\n" +
            "    String doorPassword 현관 비밀번호\n" +
            "    String phoneNumber 전화번호")
    @PutMapping(value = "/user/update")
    public synchronized ResponseModel userUpdate(
          @Valid @RequestBody AddressDto dto
    ) {
        Long userId = getAuthenticationInfoUserId();
        User user = EntityUtil.findUserId(userRepository, userId);

        ResponseModel responseModel = ResponseModel.builder().build();
        mypageService.setAddress(user, dto);

        return responseModel;
    }

   @Operation(summary = "기부현황 페이지 보기")
    @GetMapping(value = "/donation")
    public ResponseModel donation(
    )  {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("donation",mypageService.getDonation(user));
        responseModel.addData("donationCount",mypageService.getDonationCount(user));
        return responseModel;
    }

    @Operation(summary="주문현황")
    @GetMapping(value = "/order")
    public ResponseModel order(
    )  {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("order",mypageService.getOrder(user));
        responseModel.addData("orderCount",mypageService.getOrderCount(user));
        return responseModel;
    }

}
