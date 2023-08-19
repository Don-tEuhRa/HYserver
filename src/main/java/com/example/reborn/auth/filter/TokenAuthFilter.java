package com.example.reborn.auth.filter;


import com.example.reborn.auth.jwt.AuthTokenProvider;
import com.example.reborn.auth.jwt.AuthToken;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {

	private final AuthTokenProvider authTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring("Bearer ".length());
		}
		AuthToken authToken = authTokenProvider.convertToken(token);

		if (authToken.validate()) {

			Authentication authentication = authTokenProvider.getAuthentication(authToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		}


		filterChain.doFilter(request, response);
	}
}
