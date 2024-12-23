package com.api.materias.service;

import com.api.materias.model.entity.curso.Materia;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ValidadorCorrelativas {
  public Boolean puedeInscribirse(Materia materia, List<Materia> materiasAprobadas) {
    return new HashSet<>(materiasAprobadas).containsAll(materia.getCorrelativas());
  }
}
