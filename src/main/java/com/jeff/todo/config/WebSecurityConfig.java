package com.jeff.todo.config;

import com.jeff.todo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
//  private final long MAX_AGE_SECS = 3600;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
//        .csrf().disable()
        .csrf(AbstractHttpConfigurer::disable)
//        .httpBasic().disable()
        .httpBasic(AbstractHttpConfigurer::disable)
//    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .and()
//        .antMatchers("/todo_api","/todo_api/auth/**").permitAll()
//        .anyRequest().authenticated();
        .authorizeHttpRequests(
            // To permit all users to access "/" and "/api/auth/**"
            auth -> auth.requestMatchers("/", "/api/auth/**").permitAll()
//                .requestMatchers("/todo_api/login").permitAll()
//                .requestMatchers("/todo_api/test/**").permitAll()
                .anyRequest().authenticated()
        );

    http
        .addFilterAfter(
            jwtAuthenticationFilter,
            CorsFilter.class
        );

    return http.build();
  }

}
