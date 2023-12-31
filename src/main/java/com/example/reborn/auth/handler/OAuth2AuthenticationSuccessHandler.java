package com.example.reborn.auth.handler;


import com.example.reborn.auth.jwt.AuthTokenProvider;
import com.example.reborn.auth.jwt.AuthToken;
import com.example.reborn.type.dto.ResponseModel;
import com.example.reborn.type.dto.UserPrincipal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.google.common.net.UrlEscapers;



import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final long TOKEN_DURATION = 1000L * 60L * 60L * 24L;

	private final AuthTokenProvider authTokenProvider;



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
		String role = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining("|"));

		Date expiry = new Date();
		expiry.setTime(expiry.getTime() + (TOKEN_DURATION));
		AuthToken authToken = authTokenProvider.createToken(userPrincipal, expiry);
		log.debug("AuthToken Data : {}", authToken.getToken());
		ResponseModel responseModel = ResponseModel.builder()
			.message("Authorization Token Issued.")
			.build();
		response.setHeader("Authorization", authToken.getToken());
		response.setContentType("application/json");


		String servletPath = request.getServletPath();
		if (servletPath.contains("/local")) {
			response.sendRedirect(
					"http://localhost:8080/login/" + UrlEscapers.urlPathSegmentEscaper().escape(authToken.getToken()));
		} else {
			response.sendRedirect(
					"http://localhost:8080/user/login/" + UrlEscapers.urlPathSegmentEscaper().escape(authToken.getToken()));
		}
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(responseModel.toJson().getBytes());
	}
}
