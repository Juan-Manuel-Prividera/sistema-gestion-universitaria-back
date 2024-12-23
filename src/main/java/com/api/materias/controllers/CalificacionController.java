package com.api.materias.controllers;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.repository.CalificacionRepository;
import com.api.materias.service.Notificador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificaciones")
public class CalificacionController {
  private final CalificacionRepository calificacionRepository;

  public CalificacionController(CalificacionRepository calificacionRepository) {
    this.calificacionRepository = calificacionRepository;
  }


  @GetMapping("/alumnos/{idAlumno}")
  public List<Calificacion> getCalificacionesPorAlumno(@PathVariable Long idAlumno) {
    return calificacionRepository.findByAlumnoId(idAlumno);
  }

  @GetMapping("/{idCalificacion}")
  public Calificacion getCalificacionPorId(@PathVariable Long idCalificacion) {
    return calificacionRepository.findById(idCalificacion).orElse(null);
  }

  @GetMapping("/evaluacion/{idEvaluacion}")
  public List<Calificacion> getCalificacionesPorEvaluacion(@PathVariable Long idEvaluacion) {
    return calificacionRepository.findByEvaluacionId(idEvaluacion);
  }


  @PostMapping("/")
  public ResponseEntity<String> createCalificacion(@RequestBody Calificacion calificacion) {
    try {
      calificacionRepository.save(calificacion);
      Mensaje mensaje = new Mensaje("Calificacion", "Se cargo su calificacion del "
          + calificacion.getEvaluacion().getInstancia() +" del curso "+ calificacion.getCursada().getCurso().getCodigo() + " con nota " + calificacion.getNota());

      Notificador.notificar(calificacion.getAlumno(), mensaje);

      return ResponseEntity.ok("Calificacion creada");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear calificacion");
    }
  }


}