package com.example.reborn.service;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.CommentRepository;
import com.example.reborn.repository.PostRepository;
import com.example.reborn.repository.UserRepository;
import com.example.reborn.type.dto.CommentDto;
import com.example.reborn.type.entity.Comment;
import com.example.reborn.type.entity.Post;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.vo.CommentVo;
import com.example.reborn.utils.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public void save(CommentDto dto,User user, Long postId) throws PermissionDeniedException {
        //user가 null이라면 로그인 후 이용해주세요 라고 exception을 던져줘야함

        Post post = EntityUtil.findPostId(postRepository, postId);
        if(user.getRole() != Role.ADMIN){
            throw new PermissionDeniedException("관리자만 답변을 달 수 있습니다.");
        }
        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();



         commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public CommentVo listComment(Long postId, User user) {

        Post post = EntityUtil.findPostId(postRepository, postId);
         Comment comment=commentRepository.findByPost(post);
        CommentVo vo;
         if(comment==null)
            vo=new CommentVo("empty");
        else{
             vo=new CommentVo(comment);
         }


   return  vo;
    }
    @Transactional
    public void deleteComment(Long commentId, User user) throws PermissionDeniedException {
        if(user.getRole() != Role.ADMIN){
            throw new PermissionDeniedException("관리자만 답변을 삭제할 수 있습니다.");
        }

        Comment comment=commentRepository.findByCommentId(commentId).orElseThrow();
        commentRepository.delete(comment);
    }
}
