package com.api.materias.controllers;

import com.api.materias.model.entity.usuarios.Usuario;
import com.api.materias.model.repository.UsuarioRepository;
import com.api.materias.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.PagedResultsControl;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final UsuarioRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthController(UsuarioRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Usuario user) {
    try {
      Usuario usuario = userRepository.findByUsername(user.getUsername())
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

      if (!passwordEncoder.matches(usuario.getPassword(),user.getPassword())) {
        throw new RuntimeException("Credenciales incorrectas");
      }

      return ResponseEntity.ok(jwtService.generarToken(usuario));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody Usuario user) {
    try {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return ResponseEntity.ok("Usuario registrado");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
  @PostMapping("/refresh")
  public String refreshToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException("Refresh token ausente o inválido");
    }

    String refreshToken = authorizationHeader.substring(7);
    String username = jwtService.extractUsername(refreshToken); // Extraer el usuario del refresh token

    if (username != null && jwtService.validarToken(refreshToken) != null) {
      // Generar un nuevo access token
      Usuario user = userRepository.findByUsername(username)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

      return jwtService.generarToken(user);
    } else {
      throw new RuntimeException("Token inválido");
    }
  }
}
