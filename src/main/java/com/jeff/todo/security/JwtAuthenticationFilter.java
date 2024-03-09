package com.jeff.todo.security;

import com.jeff.todo.security.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try {
      // To get token from the request
      String token = parseBearerToken(request);
      log.info("Filter is running....");

      if (token != null && !token.equalsIgnoreCase("null")) {
        // To get userid from the token
        String userId = tokenProvider.validateAndGetUserId(token);
        log.info("Authenticated user ID : " + userId);

        // To register 'SecurityContextHolder'
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userId, null, AuthorityUtils.NO_AUTHORITIES
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
      }
    }catch(Exception e){
      log.error("Could not set user authentication in security context", e);
    }

    filterChain.doFilter(request, response);

  }

  private String parseBearerToken(HttpServletRequest request){
    String bearerToken = request.getHeader("Authorization");
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
      return bearerToken.substring(7);
    }
    return null;
  }
}

