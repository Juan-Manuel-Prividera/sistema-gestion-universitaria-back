package com.api.materias.service.jwt;

import com.api.materias.model.entity.usuarios.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  private static final long EXPIRATION_TIME_TOKEN = 10800000; // 3 horas
  private static final long EXPIRATION_TIME_REFRESH_TOKEN = 43200000; // 12 horas

  public String generarToken(Usuario usuario) {
    return Jwts.builder()
      .setSubject(usuario.getUsername())
      .claim("rol", usuario.getRol())
      .claim("id", usuario.getId())
      .claim("permisos", usuario.getRol().getPermisos())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_TOKEN))
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

  public String generarRefreshToken(Usuario usuario) {
    return Jwts.builder()
        .setSubject(usuario.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_REFRESH_TOKEN))
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
