package com.example.reborn.controller;


import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.service.CommentService;
import com.example.reborn.type.dto.CommentDto;
import com.example.reborn.type.dto.ResponseModel;

import com.example.reborn.type.entity.User;
import com.example.reborn.utils.EntityUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.reborn.auth.util.AuthUtil.getAuthenticationInfoUserId;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {



    private final CommentService commentService;
    private final UserRepository userRepository;

    @Operation(summary="댓글 작성",description = "String content")
    @PostMapping("/save/{postId}")
    public synchronized ResponseModel createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Long postId) throws PermissionDeniedException {
        Long userId= getAuthenticationInfoUserId();
        User user= EntityUtil.findUserId(userRepository,userId);
        commentService.save(commentDto,user,postId);
        ResponseModel responseModel = ResponseModel.builder().build();
        return responseModel;
    }
}
