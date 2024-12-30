package com.api.materias;


import com.api.materias.model.entity.curso.Materia;
import com.api.materias.service.ValidadorCorrelativas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ValidadorCorrelativasTest {
  Materia am1 = new Materia("Analisis Matematico 1", null, 1);
  Materia aga = new Materia("Algebra y geomtria analitica", null, 1);
  Materia am2 = new Materia("Analisis Matematico 2", List.of(am1,aga), 2);
  Materia ads = new Materia("Analisis de sistemas", null, 2);
  Materia dds = new Materia("Diseño de sistemas", List.of(ads), 3);

  List<Materia> materiasAprobadas;
  ValidadorCorrelativas validadorCorrelativas;

  @BeforeEach
  public void init() {
    materiasAprobadas = List.of(am1,aga);
    validadorCorrelativas = new ValidadorCorrelativas();
  }

  @Test @DisplayName("Cumple correlativas => Se puede inscribir")
  public void testCumpleCorrelativa() {
    Assertions.assertTrue(validadorCorrelativas.puedeInscribirse(am2, materiasAprobadas));
  }

  @Test @DisplayName("No cumple correlativas => No se puede inscribir")
  public void testNoCumpleCorrelativa() {
    Assertions.assertFalse(validadorCorrelativas.puedeInscribirse(dds, materiasAprobadas));
  }

}
