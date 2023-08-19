package com.example.reborn.type.entity;

import com.example.reborn.type.dto.PostDto;
import com.example.reborn.type.etc.PostCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable=true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @Column(nullable=false)
    private boolean isSecret;
    @Builder
    public Post(String title, String content, User user, LocalDateTime createdAt, LocalDateTime updatedAt,boolean isSecret,PostCategory category){
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isSecret=isSecret;
        this.category=category;
    }

    public void update(PostDto postDto){
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.updatedAt = LocalDateTime.now();
        this.isSecret=postDto.isSecret();
    }

}
