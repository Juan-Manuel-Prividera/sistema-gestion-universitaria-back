package com.api.materias;

import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.service.CalculadorPromedios;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class CalculadorPromediosTest {

  private CalculadorPromedios calculadorPromedios;
  private List<Calificacion> calificaciones;

  @BeforeEach
  public void setUp() {
    calculadorPromedios = new CalculadorPromedios();
    calificaciones = List.of(
        Calificacion.builder().nota(8D).build(),
        Calificacion.builder().nota(6D).build(),
        Calificacion.builder().nota(7D).build(),
        Calificacion.builder().nota(9D).build()
    );
  }

  @Test
  public void calcularPromedioTest() {
    Double promedio = calculadorPromedios.calcularPromedio(calificaciones.stream().map(Calificacion::getNota).toList());
    Assertions.assertEquals(7.5, promedio);
  }
}
