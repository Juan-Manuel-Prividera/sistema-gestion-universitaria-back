package com.api.materias.service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      chain.doFilter(request, response);
      return;
    }
    String token = authHeader.substring(7);
    try {
      String tokenParseado = token.replace("Bearer ", "");

      Claims claims = jwtService.validarToken(tokenParseado);
      String username = claims.getSubject();
      String rol = (String) claims.get("rol");
      List<String> permisos = (List<String>) claims.get("permisos");

      // Convierto Rol y Permisos a SimpleGrantedAuthority para que Spring Security los pueda interpretar
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));
      permisos.forEach(permiso -> authorities.add(new SimpleGrantedAuthority(permiso)));

      // Objeto autenticacion que usa Spring Security
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(username, null, authorities);

      SecurityContextHolder.getContext().setAuthentication(authToken);

      chain.doFilter(request, response);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
  }
}
