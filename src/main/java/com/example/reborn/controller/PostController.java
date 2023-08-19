package com.example.reborn.controller;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.CommentRepository;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.CommentService;
import com.example.reborn.service.PostService;
import com.example.reborn.type.dto.PostDto;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.vo.CommentVo;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final UserRepository userRepository;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    @Operation(summary = "게시물 생성")
    @PostMapping(value="/create")
    public synchronized ResponseModel createPost(@Valid @RequestBody PostDto postDto) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        postService.createPost(postDto, user);
        ResponseModel responseModel = ResponseModel.builder().build();
        return ResponseModel.builder().build();
    }

    @Operation(summary = "게시물 수정")
    @PutMapping(value="/update/{postId}")
    public synchronized ResponseModel updatePost(@PathVariable Long postId,  @Valid @RequestBody PostDto postDto) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        postService.updatePost(postId, postDto, user);
        return ResponseModel.builder().build();
    }


    @Operation(summary = "게시물 삭제")
    @DeleteMapping(value="/delete/{postId}")
    public synchronized ResponseModel deletePost(@PathVariable Long postId) throws PermissionDeniedException {

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        postService.deletePost(postId, user);
        return ResponseModel.builder().build();
    }


    @Operation(summary = "게시물 보기")
    @GetMapping(value="/read/{postId}")
    public ResponseModel readPost(@PathVariable Long postId){

        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("post", postService.readPost(postId, user));
        CommentVo vo=commentService.listComment(postId,user);
        if(vo!=null)
            responseModel.addData("comment",vo);
        return responseModel;
    }


    @Operation(summary="qna 리스트")
    @GetMapping(value = "/list")
    public ResponseModel listPost()
    {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        ResponseModel responseModel = ResponseModel.builder().build();
        responseModel.addData("order" ,postService.listOrderPost(user));
        responseModel.addData("donation",postService.listDonationPost(user));
        return responseModel;

    }


}
