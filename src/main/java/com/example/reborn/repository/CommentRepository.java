package com.example.reborn.repository;

import com.example.reborn.type.entity.Comment;
import com.example.reborn.type.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);

    Comment findByPost(Post post);

    boolean existsCommentByPost(Post posts);

  Optional<Comment> findByCommentId(Long commentId);
}
