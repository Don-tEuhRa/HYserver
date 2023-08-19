package com.example.reborn.auth.util;

import com.example.reborn.auth.exception.UnAuthorizeException;
import com.example.reborn.type.dto.UserPrincipal;
import com.example.reborn.type.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class AuthUtil {

    public static User getAuthenticationInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("AuthUtil Authentication: {}", authentication);
        return ((UserPrincipal) authentication.getPrincipal()).toUser();
    }


    public static Long getAuthenticationInfoUserId() throws UnAuthorizeException {

        try {
            return getAuthenticationInfo().getUserId();
        } catch (NumberFormatException | NullPointerException e) {
            throw new UnAuthorizeException();
        }
    }




}