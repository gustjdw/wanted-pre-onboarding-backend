package com.backend.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 토큰 받아오기
        String token = jwtProvider.resolveToken(request);

        // 토큰이 유효하다면
        if (token != null && jwtProvider.validateToken(token)) {

            // 토큰으로부터 유저 정보를 받아
            Authentication authentication = jwtProvider.getAuthentication(token);

            // SecurityContext 에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 Filter 실행
        filterChain.doFilter(request, response);
    }
}
