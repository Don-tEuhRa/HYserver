package com.example.reborn.type.dto;


import com.example.reborn.type.etc.OAuthProvider;
import com.example.reborn.type.etc.Role;
import lombok.*;




@Getter
@Setter
public class UserDto {

    private long userId;

    private String nickname;

    private String email;

    private Role role;

    private OAuthProvider oauth_provider;

    private Long point;


}
