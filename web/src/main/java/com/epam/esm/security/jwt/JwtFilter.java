package com.epam.esm.security.jwt;

import com.epam.esm.exception.JwtAuthenticationException;
import com.epam.esm.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String token = getTokenFromRequest(request);
       try {

           if (token != null && jwtProvider.validateToken(token)) {
               Authentication authentication = jwtProvider.getAuthentication(token);
               if (authentication != null) {
                   SecurityContextHolder.getContext().setAuthentication(authentication);
               }
           }
       } catch (JwtAuthenticationException e) {
           SecurityContextHolder.clearContext();
           throw new JwtAuthenticationException("JWT token is expired or invalid");
       }
        filterChain.doFilter(request, response);
    }

    protected String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}

