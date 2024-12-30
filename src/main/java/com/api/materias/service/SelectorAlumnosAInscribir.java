package com.api.materias.service;

import com.api.materias.model.entity.curso.Cursada;
import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.repository.CursadaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SelectorAlumnosAInscribir {
  private final CursadaRepository cursadaRepository;

  public SelectorAlumnosAInscribir(CursadaRepository cursadaRepository) {
    this.cursadaRepository = cursadaRepository;
  }

  public List<Alumno> ordernarAlumnos(List<Alumno> alumnos) {
    alumnos.sort((a1, a2) -> {
      int pesoAcademico1 = calcularPesoAcademico(a1);
      int pesoAcademico2 = calcularPesoAcademico(a2);
      return pesoAcademico2 - pesoAcademico1;
    });
    return alumnos;
  }

  public int calcularPesoAcademico(Alumno alumno) {
    int cursadasAprobadas = 0;
    int cursadasDesaprobadas = 0;
    int tiempoEnFacultad = LocalDate.now().getYear() - alumno.getFechaAlta().getYear();
    List<Cursada> cursadas = cursadaRepository.findByAlumnoId(alumno.getId());
    for (Cursada cursada : cursadas) {
      if (cursada.aprobo())
        cursadasAprobadas++;
      else
        cursadasDesaprobadas++;
    }
    return 11 * cursadasAprobadas - 3 * cursadasDesaprobadas -5 * tiempoEnFacultad;
  }
}
