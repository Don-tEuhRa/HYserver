package com.example.reborn.config;

import com.example.reborn.auth.jwt.AuthTokenProvider;
import com.example.reborn.auth.jwt.AuthTokenProviderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret-key}")
    private String secretKey;



    @Bean
    public AuthTokenProvider authTokenProvider() {
        return new AuthTokenProviderImpl(secretKey);
    }





}