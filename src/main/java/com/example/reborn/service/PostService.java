package com.example.reborn.service;

import com.example.reborn.exception.PermissionDeniedException;
import com.example.reborn.repository.CommentRepository;
import com.example.reborn.repository.PostRepository;
import com.example.reborn.type.dto.PostDto;
import com.example.reborn.type.entity.Post;
import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.PostCategory;
import com.example.reborn.type.etc.Role;
import com.example.reborn.type.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public  boolean isMe(Post post, User user)
    {
        if(post.getUser()==user)
            return true;
        return false;
    }

    public void createPost(PostDto postDto, User user) throws PermissionDeniedException {
        if(user.getRole() != Role.USER){
            throw new PermissionDeniedException("로그인을 해야 리뷰를 작성할 수 있습니다.");
        }
        if((postDto.inputTest(postDto)))
        {
        //유효성검사
        Post post = Post.builder().
                title(postDto.getTitle()).
                content(postDto.getContent()).
                isSecret(postDto.isSecret()).
                user(user).
                category(postDto.getCategory()).
                createdAt(LocalDateTime.now()).
                updatedAt(LocalDateTime.now()).
                build();
        postRepository.save(post);}
 else {
            throw new IllegalArgumentException("값을 모두 입력해주세요");
        }

    }

    public void updatePost(Long postId,PostDto newPostDto, User user) throws PermissionDeniedException {

        Post post = postRepository.findByPostId(postId).orElseThrow(()-> new IllegalArgumentException("해당 리뷰가 없습니다."));

        if(post.getUser().getUserId() != user.getUserId()){
            throw new PermissionDeniedException("작성자만 리뷰를 수정할 수 있습니다.");
        }

        post.update(newPostDto);
        postRepository.save(post);
    }

    public void deletePost(Long postId, User user) throws PermissionDeniedException {
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NotFoundException("해당 리뷰가 없습니다."));

        if(post.getUser().getUserId() != user.getUserId()){
            throw new PermissionDeniedException("작성자만 리뷰를 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }

    public PostVo readPost(Long postId, User user){
        Post post = postRepository.findByPostId(postId).orElseThrow(() -> new NotFoundException("해당 리뷰가 없습니다."));
        boolean me=isMe(post,user);

        return new PostVo(post,user,me);

    }

    public List<PostVo> listOrderPost(User user)
    {
        List<Post> postList=postRepository.findAllByCategory(PostCategory.구매문의);
        List<PostVo> post = new ArrayList<>();

        boolean isReply=false;
        for(Post posts: postList)
        {
            isReply=commentRepository.existsCommentByPost(posts);
            PostVo postVo=new PostVo(posts,isMe(posts,user),user,isReply);
            post.add(postVo);

        }
        return post;
    }

    public List<PostVo> listDonationPost(User user)
    {
        List<Post> postList=postRepository.findAllByCategory(PostCategory.기부문의);
        List<PostVo> post = new ArrayList<>();
        boolean isReply=false;
        for(Post posts: postList)
        {     isReply=commentRepository.existsCommentByPost(posts);
            PostVo postVo=new PostVo(posts,isMe(posts,user),user,isReply);
            post.add(postVo);
        }
        return post;
    }

}
