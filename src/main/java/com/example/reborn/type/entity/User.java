package com.example.reborn.type.entity;

import com.example.reborn.type.etc.OAuthProvider;
import com.example.reborn.type.etc.Role;
import javax.persistence.*;

import lombok.*;



@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(nullable = false)
    private String nickname;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oauth_provider;

    @Column(columnDefinition = "TEXT",nullable = true)
    private String profileImgUrl;

    @Column(nullable = false)
    private Boolean isDonated;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = true)
    private Long point;

    @Builder
    public User(Long userId,String nickname, String name,String email, Role role, OAuthProvider oauth_provider, Long point,String phone,boolean isDonated){

        this.userId = userId;
        this.nickname = nickname;
        this.name=name;
        this.email = email;
        this.role = role;
        this.oauth_provider = oauth_provider;
        this.point = point;
        this.phone=phone;
        this.isDonated=isDonated;
    }

    public User(Boolean isDonated){
        this.isDonated = isDonated;
    }

    public User(Long userId) {
        this.userId = userId;
    }

}