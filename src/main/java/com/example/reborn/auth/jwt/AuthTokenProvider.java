package com.example.reborn.auth.jwt;

import com.example.reborn.type.dto.UserPrincipal;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface AuthTokenProvider {
    AuthToken createToken(String id, String role, Date expiry);
    AuthToken createToken(UserPrincipal userPrincipal, Date expiry);

    AuthToken convertToken(String token);

    Authentication getAuthentication(AuthToken authToken);
}