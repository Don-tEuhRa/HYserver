package com.example.reborn.service;

import com.example.reborn.repository.ReviewRepository;
import com.example.reborn.repository.SurveyRepository;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.type.dto.ReviewDto;
import com.example.reborn.type.entity.Review;
import com.example.reborn.type.entity.Survey;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.etc.SurveyQuestion;
import com.example.reborn.type.vo.ReviewVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final SurveyRepository surveyRepository;


    public void saveReview(ReviewDto reviewDto, User user) {

        if(user.getRole() != Role.USER){
            throw new IllegalArgumentException("로그인을 해야 리뷰를 작성할 수 있습니다.");
        }
        if(reviewDto.getContent().length() > 50){
            throw new IllegalArgumentException("리뷰는 50자 이내로 작성해주세요.");
        }
        if(reviewDto.getStar() > 5 || reviewDto.getStar() < 1){
            throw new IllegalArgumentException("별점은 1~5점 사이로 작성해주세요.");
        }

        Review review = Review.builder()
                .user(user)
                .content(reviewDto.getContent())
                .star(reviewDto.getStar())
                .createdAt(LocalDateTime.now())
                .build();
        reviewRepository.save(review);

        Long i = 1L;
        for(Long w : reviewDto.getWeight()){
            if(w > 5 || w < 1) {
                throw new IllegalArgumentException("올바르지 않은 값이 입력되었습니다.");
            }
            Survey survey = Survey.builder()
                    .weight(w)
                    .surveyQuestion(SurveyQuestion.findByValue(i))
                    .review(review)
                    .build();
            surveyRepository.save(survey);
            i++;
        }

        user.setIsDonated(false);


    }


    public ReviewVo findReviewRecent() {

        Review review = reviewRepository.findTopByOrderByCreatedAtDesc().orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        return new ReviewVo(review);
    }


    public List<ReviewVo> findReviewList() {

        List<Review> reviewList = reviewRepository.findAll();
        return reviewList.stream()
                .map(review -> new ReviewVo(review))
                .collect(Collectors.toList());
    }


    public Boolean checkReviewPermission(User user){

        if(user.getRole() != Role.USER){
            throw new IllegalArgumentException("로그인을 해야 리뷰를 작성할 수 있습니다.");
        }

        if (!user.getIsDonated()){
            return false;
        }

        return true;
    }

}
