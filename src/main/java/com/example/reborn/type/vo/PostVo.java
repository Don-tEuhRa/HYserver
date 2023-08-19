package com.example.reborn.type.vo;


import com.example.reborn.type.entity.Post;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.PostCategory;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class PostVo {

    private Long postId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PostCategory category;



    private boolean isMe;

    private boolean isSecret;

    private String name;

    private boolean isReply;




    public PostVo(Post post, boolean isMe,User user, boolean isReply)
    {

        this.name=user.getName();
        this.postId=post.getPostId();
        this.title= post.getTitle();;
        this.content= post.getContent();
        this.isSecret=post.isSecret();
        this.isMe=isMe;
        this.createdAt=post.getCreatedAt();
        this.isReply=isReply;
        this.category=post.getCategory();

    }
    public PostVo(Post post, User user,boolean isMe)
    {
this.isMe=isMe;
        this.name=user.getName();
        this.postId=post.getPostId();
        this.title= post.getTitle();;
        this.content= post.getContent();
        this.isSecret=post.isSecret();
        this.category=post.getCategory();
        this.createdAt=post.getCreatedAt();
    }
}
