package com.example.reborn.repository;

import com.example.reborn.type.entity.User;
import com.example.reborn.type.entity.UserAddressRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAddressRelationRepository extends JpaRepository<UserAddressRelation, Long> {
    Optional<UserAddressRelation> findByUserId(User user);



}
