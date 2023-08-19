package com.example.reborn.type.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;


@Entity
@Table(name = "review")
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    // 50자 제한
    @Column(columnDefinition = "TEXT",nullable=true)
    private String content;

    @Column(nullable = false)
    private Long star;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;


    @Builder
    public Review(User user, String content, Long star,LocalDateTime createdAt){
        this.user = user;
        this.content = content;
        this.star = star;
        this.createdAt = createdAt;
    }


}
