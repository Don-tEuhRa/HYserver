package com.example.reborn.type.vo;

import com.example.reborn.type.entity.Comment;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentVo {


    private String reviewContent;
    private LocalDateTime reviewDate;


    public CommentVo(Comment comment)
    {
        this.reviewContent=comment.getContent();
        this.reviewDate=comment.getCreatedAt();
    }
    public CommentVo(String empty)
    {
        this.reviewContent="";
        this.reviewDate=LocalDateTime.now();
    }
}
