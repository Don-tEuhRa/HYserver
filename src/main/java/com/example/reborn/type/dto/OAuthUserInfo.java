package com.example.reborn.type.dto;

import java.util.HashMap;
import java.util.Map;
import com.example.reborn.auth.exception.UnAuthorizeException;

public abstract class OAuthUserInfo {

    protected Map<String, Object> attributes;

    public OAuthUserInfo(Map<String, Object> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId() throws UnAuthorizeException;

    public abstract String getOAuthProviderName();

    public abstract String getEmail() throws UnAuthorizeException;

    public abstract String getName() throws UnAuthorizeException;

    public abstract String getPicture() throws UnAuthorizeException;

}