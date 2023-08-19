package com.example.reborn.repository;

import com.example.reborn.type.entity.Post;
import com.example.reborn.type.etc.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostId(Long postId);

    List<Post> findAllByCategory(PostCategory category);

}
