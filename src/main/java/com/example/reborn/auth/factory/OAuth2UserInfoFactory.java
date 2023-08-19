package com.example.reborn.auth.factory;



import com.example.reborn.type.dto.GoogleOAuthUserInfo;
import com.example.reborn.type.dto.OAuthUserInfo;
import com.example.reborn.type.etc.OAuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuthUserInfo of(OAuthProvider oAuthProvider, Map<String, Object> attributes) throws
		IllegalArgumentException {
		switch (oAuthProvider) {
			case GOOGLE:
				return new GoogleOAuthUserInfo(attributes);
			default:
				throw new IllegalArgumentException("OAuthProvider Not Excepted!!");
		}
	}
}
