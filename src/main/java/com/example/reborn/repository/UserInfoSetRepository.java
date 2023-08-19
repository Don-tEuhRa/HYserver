package com.example.reborn.repository;

import com.example.reborn.type.entity.UserInfoSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoSetRepository extends JpaRepository<UserInfoSet, Long> {
}
