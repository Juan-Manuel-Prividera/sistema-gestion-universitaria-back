package com.api.materias.controllers;

import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.entity.personas.Docente;
import com.api.materias.model.entity.usuarios.Usuario;
import com.api.materias.model.repository.AlumnoRepository;
import com.api.materias.model.repository.DocenteRepository;
import com.api.materias.model.repository.RolRepository;
import com.api.materias.model.repository.UsuarioRepository;
import com.api.materias.service.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final RolRepository rolRepository;
  private final UsuarioRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AlumnoRepository alumnoRepository;
  private final DocenteRepository docenteRepository;

  public AuthController(RolRepository rolRepository, UsuarioRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AlumnoRepository alumnoRepository, DocenteRepository docenteRepository) {
    this.rolRepository = rolRepository;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
    this.alumnoRepository = alumnoRepository;
    this.docenteRepository = docenteRepository;
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody Usuario user) {
    try {
      Usuario usuario = userRepository.findByUsername(user.getUsername())
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

      if (!passwordEncoder.matches(user.getPassword(),usuario.getPassword())) {
        throw new RuntimeException("Credenciales incorrectas");
      }
      // TODO: Mejorar :)
      Alumno alumno = alumnoRepository.findByUsuario(usuario);
      Docente docente = docenteRepository.findByUsuario(usuario);

      String accessToken;
      if (alumno != null) {
        accessToken = jwtService.generarToken(usuario, alumno);
      } else if (docente != null) {
        accessToken = jwtService.generarToken(usuario, docente);
      } else {
        accessToken = jwtService.generarToken(usuario);
      }
      String refreshToken = jwtService.generarRefreshToken(usuario);

      Map<String, String> tokens = new HashMap<>();
      tokens.put("accessToken", accessToken);
      tokens.put("refreshToken", refreshToken);

      // Si se logeo correctamente, se generan los tokens y se envian al cliente
      return ResponseEntity.ok(tokens.toString());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/register/{rol}")
  public ResponseEntity<String> register(@RequestBody Usuario user, @PathVariable String rol) {
    try {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setRol(rolRepository.findByRol(rol.toUpperCase())
          .orElseThrow(() -> new RuntimeException("Rol "+ rol +" no encontrado")));

      userRepository.save(user);
      // No genera token ya que luego de registrarse debe logearse
      return ResponseEntity.ok("Usuario registrado");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refreshToken(HttpServletRequest request) {
    try {
      String authorizationHeader = request.getHeader("Authorization");

      if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        throw new RuntimeException("Refresh token ausente o inválido");
      }

      String refreshToken = authorizationHeader.substring(7);
      String username = jwtService.extractUsername(refreshToken);

      if (username != null && jwtService.validarToken(refreshToken) != null) {
        // Generar un nuevo access token
        Usuario user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(jwtService.generarToken(user));
      } else {
        throw new RuntimeException("Token inválido");
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}
