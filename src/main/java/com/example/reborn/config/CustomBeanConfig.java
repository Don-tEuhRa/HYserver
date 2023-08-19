package com.example.reborn.config;


import com.example.reborn.utils.NicknameGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class CustomBeanConfig {


    @Bean
    public NicknameGenerator nicknameGenerator() throws IOException {
        return new NicknameGenerator();
    }




}