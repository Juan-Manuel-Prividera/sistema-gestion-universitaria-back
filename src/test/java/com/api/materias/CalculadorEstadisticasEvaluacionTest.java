package com.api.materias;


import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.entity.evaluaciones.Evaluacion;
import com.api.materias.service.CalculadorEstadisticasEvaluacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculadorEstadisticasEvaluacionTest {

  private CalculadorEstadisticasEvaluacion calculadorEstadisticasEvaluacion;

  @BeforeEach
  public void setUp() {
    calculadorEstadisticasEvaluacion = new CalculadorEstadisticasEvaluacion();
  }

  @Test
  public void testCalcularEstadisticas() {
    Evaluacion evaluacion = new Evaluacion();
    Calificacion cal1 = mock(Calificacion.class);
    Calificacion cal2 = mock(Calificacion.class);
    Calificacion cal3 = mock(Calificacion.class);

    when(cal1.estaAprobado()).thenReturn(true);
    when(cal1.getNota()).thenReturn(8.0);

    when(cal2.estaAprobado()).thenReturn(false);
    when(cal2.getNota()).thenReturn(4.0);

    when(cal3.estaAprobado()).thenReturn(true);
    when(cal3.getNota()).thenReturn(6.0);

    List<Calificacion> calificaciones = Arrays.asList(cal1, cal2, cal3);

    Evaluacion resultado = calculadorEstadisticasEvaluacion.calcularEstadisticas(evaluacion, calificaciones);

    assertEquals(8.0, resultado.getNotaMaxima());
    assertEquals(4.0, resultado.getNotaMinima());
    assertEquals(6.0, resultado.getNotaPromedio());
    assertEquals(66.66666666666666, resultado.getProcentajeAprobacion());
  }
}
