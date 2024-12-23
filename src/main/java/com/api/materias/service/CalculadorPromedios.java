package com.api.materias.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculadorPromedios {
  public Double calcularPromedio(List<Double> calificaciones) {
    Double promedio = 0.0;
    for (Double calificacion : calificaciones) {
      promedio += calificacion;
    }
    return promedio / calificaciones.size();
  }
}
