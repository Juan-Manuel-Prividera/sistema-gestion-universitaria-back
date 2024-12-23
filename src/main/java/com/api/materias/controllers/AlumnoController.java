package com.api.materias.controllers;

import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.repository.AlumnoRepository;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.model.repository.InscripcionRepository;
import com.api.materias.service.CalculadorPromedios;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
  private final AlumnoRepository alumnoRepository;
  private final CalculadorPromedios calculadorPromedios;
  private final CursadaRepository cursadaRepository;
  private final InscripcionRepository inscripcionRepository;

  public AlumnoController(AlumnoRepository alumnoRepository, CalculadorPromedios calculadorPromedios,
                          CursadaRepository cursadaRepository, InscripcionRepository inscripcionRepository) {
    this.alumnoRepository = alumnoRepository;
    this.calculadorPromedios = calculadorPromedios;
    this.cursadaRepository = cursadaRepository;
    this.inscripcionRepository = inscripcionRepository;
  }

  @GetMapping("/{idAlumno}")
  public Alumno getAlumno(@PathVariable Long idAlumno) {
    return alumnoRepository.findById(idAlumno).orElse(null);
  }

  @GetMapping("/curso/{idCurso}")
  public List<Alumno> getAlumnosPorCurso(@PathVariable Long idCurso) {
    List<Alumno> alumnos = new ArrayList<>();
    inscripcionRepository.findByCursoId(idCurso)
        .forEach(inscripcion -> {alumnos.add(inscripcion.getAlumno());});
    return alumnos;
  }

  @GetMapping("/{idAlumno}/promedio/total")
  public Double getPromedioTotal(@PathVariable Long idAlumno) {
    List<Double> calificaciones = new ArrayList<>();
    cursadaRepository.findByAlumnoId(idAlumno)
        .forEach(cursada -> {calificaciones.add(cursada.getCalificacionFinal());});
    return calculadorPromedios.calcularPromedio(calificaciones);
  }

  @GetMapping("/{idAlumno}/promedio/nivel/{nivel}")
  public Double getPromedioPorNivel(@PathVariable Long idAlumno, @PathVariable Integer nivel) {
    List<Double> calificaciones = new ArrayList<>();
    cursadaRepository.findByAlumnoIdAndNivel(idAlumno, nivel)
        .forEach(cursada -> {calificaciones.add(cursada.getCalificacionFinal());});
    return calculadorPromedios.calcularPromedio(calificaciones);
  }


  @PostMapping("/")
  public ResponseEntity<String> crearAlumno(@RequestBody Alumno alumno) {
    try {
      alumnoRepository.save(alumno);
      return ResponseEntity.ok().body("Alumno creado con exito!");
    } catch (Exception e) {
      return ResponseEntity.status(500).body(e.getMessage());
    }
  }

  @PostMapping("/{idAlumno}")
  public ResponseEntity<String> actualizarAlumno(@PathVariable Long idAlumno, @RequestBody Alumno alumno) {
    if (alumnoRepository.findById(idAlumno).isPresent()) {
      Alumno alumnoActual = alumnoRepository.findById(idAlumno).get();
      alumnoActual.update(alumno);
      alumnoRepository.save(alumnoActual);
      return ResponseEntity.ok("Alumno actualizado con exito!");
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
