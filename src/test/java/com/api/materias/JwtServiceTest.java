package com.api.materias;

import com.api.materias.model.entity.usuarios.Permiso;
import com.api.materias.model.entity.usuarios.Rol;
import com.api.materias.model.entity.usuarios.Usuario;
import com.api.materias.service.security.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JwtServiceTest {
  private JwtService jwtService;

  @BeforeEach
  public void init() {
    jwtService = new JwtService();
  }


  @Test
  public void generarYValidarToken() {
    Usuario usuario = new Usuario("admin", "admin", new Rol("ADMIN", List.of(new Permiso("GET_MATERIAS"))));
    String token = jwtService.generarToken(usuario);
    Assertions.assertNotNull(jwtService.validarToken(token));
  }
}
