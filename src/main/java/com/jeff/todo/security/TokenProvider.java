package com.jeff.todo.security;

import com.jeff.todo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {

  //   private static final String SECRET_KEY = "dlT0mdt1JqD3idg4MlD5odL6wOd7Ndl8dbS9DKrKSkekFakqkTkdKWKCKZkvkXkgkDNflskfKeoGKSalsrNRod";
  /* To use HS512, key(String) size should be at least 512 bits */
  private static final String base64EncodedSecretKey = "dlT0mdt1JqD3idg4MlD5odL6wOd7Ndl8dbS9DKrKSkekFakqkTkdKWKCKZkvkXkgkDNflskfKeoGKSalsrNRod";
  byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
  Key key = Keys.hmacShaKeyFor(keyBytes);

  public String create(UserEntity userEntity) {
    Date expiryDate = Date.from(
        Instant.now()
            .plus(1, ChronoUnit.DAYS)
    );

    /*
    {
      // header
      "alg":"HS512"
    }.
    {
      // payload
      "sub": "",
      "iss": "demo app",
      "iat": 1596597657,
      "exp":1596597657
    }.
    // SECRET_KEY
     */

    return Jwts.builder()
        .signWith(key, SignatureAlgorithm.HS512)
        .setSubject(userEntity.getId())
        .setIssuer("demo app")
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .compact();
  }

  public String validateAndGetUserId(String token){

    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }
}
