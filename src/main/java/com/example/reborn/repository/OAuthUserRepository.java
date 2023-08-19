package com.example.reborn.repository;

import com.example.reborn.type.entity.OAuthUser;
import com.example.reborn.type.etc.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Long> {
    Optional<OAuthUser> findByProviderUserIdAndOap(String providerUserId, OAuthProvider oap);

}
