package com.api.materias.service.jwt;

import com.api.materias.model.entity.usuarios.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static javax.crypto.Cipher.SECRET_KEY;


@Service
public class JwtService {
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final long EXPIRATION_TIME = 86400000; // 1 día

  public String generarToken(Usuario usuario) {
    return Jwts.builder()
      .setSubject(usuario.getUsername())
      .claim("rol", usuario.getRol())
      .claim("id", usuario.getId())
      .claim("permisos", usuario.getRol().getPermisos())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(key)
      .compact();
  }

  public Map<String, Object> validarToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .getBody();

  }

  public String generateRefreshToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 días
        .signWith(key)
        .compact();
  }


  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }



}
