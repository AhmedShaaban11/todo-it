package com.ahmed.todo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class JwtService {
  private static final String SECRET = "secret";
  public static final Integer EXPIRY_SECONDS = 60 * 30; // 30 minutes

  public String createToken(String username) {
    return JWT.create()
        .withJWTId(UUID.randomUUID().toString())
        .withIssuedAt(Instant.now())
        .withExpiresAt(Instant.now().plusSeconds(EXPIRY_SECONDS))
        .withSubject(username)
        .sign(Algorithm.HMAC256(SECRET));
  }

  public String verifyToken(String token) {
    return JWT.require(Algorithm.HMAC256(SECRET))
        .build()
        .verify(token)
        .getSubject();
  }
}
