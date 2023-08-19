package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Review;
import com.example.reborn.type.entity.User;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ReviewVo {

    private String userName;

    private String content;

    private Long star;

    private LocalDateTime createdAt;

    public ReviewVo(User user, String content, Long star, LocalDateTime createdAt){
        this.userName = user.getName();
        this.content = content;
        this.star = star;
        this.createdAt = createdAt;
    }

    public ReviewVo(Review review){
        this.userName = review.getUser().getName();
        this.content = review.getContent();
        this.star = review.getStar();
        this.createdAt = review.getCreatedAt();
    }
}
