package com.example.reborn.type.dto;

import com.example.reborn.type.entity.User;
import com.example.reborn.type.etc.OAuthProvider;
import com.example.reborn.type.etc.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private final Long userId;

    private final String name;
    private final String email;

    private final Role role;
    private final OAuthProvider provider;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getPassword() {
        return "[PROTECTED]";
    }

    @Override
    public String getUsername() {
        return Long.toString(userId);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> clone = new HashMap<>();
        for (String key : attributes.keySet()) {
            Object value = attributes.get(key);
            clone.put(key, value);
        }
        return clone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    @Override
    public String getName() {
        return "[" + userId + " : " + name + "]";
    }

    public static UserPrincipal create(User user) {

        return new UserPrincipal(user.getUserId(),user.getNickname(), user.getEmail(), user.getRole(), user.getOauth_provider(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())), new HashMap<>());

    }

    public User toUser() {
        User user = User.builder()
                .userId(userId)
                .nickname(name)
                .email(email)
                .role(role)
                .oauth_provider(provider)
                .build();
        return user;
    }

}