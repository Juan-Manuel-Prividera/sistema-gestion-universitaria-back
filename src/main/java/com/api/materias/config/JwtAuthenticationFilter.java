package com.api.materias.config;

import com.api.materias.model.entity.usuarios.Permiso;
import com.api.materias.model.entity.usuarios.Rol;
import com.api.materias.service.jwt.JwtService;
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
    Claims claims = (Claims) jwtService.validarToken(token);
    String username = claims.getSubject();
    Rol rol = (Rol) claims.get("roles");
    List<Permiso> permisos = (List<Permiso>) claims.get("permissions");

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getRol()));
    permisos.forEach(permiso -> authorities.add(new SimpleGrantedAuthority(permiso.getPermiso())));

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(username, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authToken);

    chain.doFilter(request, response);
  }
}
