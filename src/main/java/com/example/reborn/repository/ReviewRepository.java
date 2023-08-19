package com.example.reborn.repository;

import com.example.reborn.type.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);

    // createdAt이 가장 최근인 리뷰를 가져온다.
    Optional<Review> findTopByOrderByCreatedAtDesc();
}
