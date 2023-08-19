package com.example.reborn.controller;

import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.ReviewService;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.dto.ReviewDto;

import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.SurveyQuestion;
import com.example.reborn.type.vo.ReviewVo;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/Review")
public class ReviewController {

    private final ReviewService reviewService;

    private final UserRepository userRepository;

    @Operation(summary="리뷰 저장")
    @PostMapping(value="/{userId}")
    public synchronized ResponseModel createReview( @Valid @RequestBody ReviewDto reviewDto){

        Long userId = getAuthenticationInfoUserId();
        User user = EntityUtil.findUserId(userRepository, userId);
        reviewService.saveReview(reviewDto,user);

        return ResponseModel.builder().build();
    }


    @Operation(summary="최근 리뷰 조회")
    @GetMapping(value="/recent")
    public ResponseModel readReviewRecent() {

        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("review",  reviewService.findReviewRecent());

        return responseModel;
    }

    @Transactional(readOnly = true)
    @Operation(summary="리뷰 리스팅")
    @GetMapping(value="/list")
    public ResponseModel readReviewList() {

        ResponseModel responseModel = ResponseModel.builder().build();
        List<ReviewVo> reviewList = reviewService.findReviewList();
        responseModel.addData("reviewList", reviewList);

        return responseModel;
    }

    @Operation(summary="설문 및 권한 체크")
    @GetMapping(value="/{userId}")
    public synchronized ResponseModel reviewInfo(@PathVariable Long userId){

        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        Boolean isReviewPermission = reviewService.checkReviewPermission(user);
        responseModel.addData("isReviewPermission", isReviewPermission);
        responseModel.addData("survey", SurveyQuestion.getSurveyQuestion());

        return responseModel;
    }


}