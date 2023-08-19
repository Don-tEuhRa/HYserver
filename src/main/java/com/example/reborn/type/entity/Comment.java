package com.example.reborn.type.entity;

import javax.persistence.*;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @Column(nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @Builder
    public Comment(String content, User user, Post post, LocalDateTime createdAt){

        this.content = content;
        this.post = post;
        this.user=user;
        this.createdAt = createdAt;


    }
}
