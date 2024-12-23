package com.api.materias.service;

import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.entity.evaluaciones.Evaluacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculadorEstadisticasEvaluacion {
  public Evaluacion calcularEstadisticas(Evaluacion evaluacion, List<Calificacion> calificaciones) {
    int aprobados = 0;
    int desaprobados = 0;
    Double notaMaxima = null;
    Double notaMinima = null;
    Double sumNotas = 0.0;
    for (Calificacion calificacion : calificaciones) {
      if (calificacion.estaAprobado())
        aprobados++;
      else
        desaprobados++;

      if (calificacion.getNota() != null) {
        if (notaMaxima == null || calificacion.getNota() > notaMaxima)
          notaMaxima = calificacion.getNota();
        if (notaMinima == null || calificacion.getNota() < notaMinima)
          notaMinima = calificacion.getNota();
      }
      sumNotas += calificacion.getNota();
    }
    Double poercentajeAprobacion = (double) aprobados / calificaciones.size() * 100;
    Double promedio = sumNotas / calificaciones.size();
    evaluacion.setNotaMaxima(notaMaxima);
    evaluacion.setNotaMinima(notaMinima);
    evaluacion.setNotaPromedio(promedio);
    evaluacion.setProcentajeAprobacion(poercentajeAprobacion);

    return evaluacion;
  }
}
