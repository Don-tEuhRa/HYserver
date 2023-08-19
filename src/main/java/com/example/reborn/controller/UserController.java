package com.example.reborn.controller;

import com.example.reborn.auth.jwt.AuthToken;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.UserService;
import com.example.reborn.type.dto.ResponseModel;

import com.example.reborn.type.entity.User;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {



    private final UserService userService;

    private final UserRepository userRepository;



    @Operation(summary="유저 정보 조회" ,description = "")
    @RequestMapping(value="/info", method = {RequestMethod.GET})
    public ResponseModel info() {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);

        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("user", userService.getUser(user));

        return responseModel;
    }


    @Operation(summary = "Firebase 유저 연동", description = "Firebase를 통해 가입한 유저 MariaDB 연동" +
            "\n PathVariable : uid(Firebase uid)")
//    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/firebase/{uid}", method = RequestMethod.POST)
    public ResponseModel saveUserFromFirebase(@PathVariable String uid) {
        User user = userService.saveUserFromFirebase(uid);
        AuthToken authToken = userService.getUserToken(user);
        log.debug("AuthToken Data : {}", authToken.getToken());
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("user", user);
        responseModel.addData("jwt", authToken.getToken());
        return responseModel;
    }
}