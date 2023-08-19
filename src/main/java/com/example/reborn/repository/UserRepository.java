package com.example.reborn.repository;

import com.example.reborn.type.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserId(Long userId);

    Optional<User> findByNickname(String nickname);

User findTopByOrderByUserIdDesc();

    List<User> findAllByEmail(String email);

}
