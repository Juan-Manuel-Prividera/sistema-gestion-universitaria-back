package com.api.materias.controllers;


import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.curso.*;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.model.repository.InscripcionRepository;
import com.api.materias.service.Notificador;
import com.api.materias.service.ValidadorCorrelativas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {
  private final ValidadorCorrelativas validadorCorrelativas;
  private final InscripcionRepository inscripcionRepository;
  private final CursadaRepository cursadaRepository;

  public InscripcionController(ValidadorCorrelativas validadorCorrelativas, InscripcionRepository inscripcionRepository, CursadaRepository cursadaRepository) {
    this.validadorCorrelativas = validadorCorrelativas;
    this.inscripcionRepository = inscripcionRepository;
    this.cursadaRepository = cursadaRepository;
  }

  @PostMapping("/")
  public ResponseEntity<String> inscribir(@RequestBody Inscripcion inscripcionRequest) {
    try {
      List<Materia> materiasAprobadas = cursadaRepository
          .findByAlumnoId(inscripcionRequest.getAlumno().getId())
          .stream().filter(cursada -> {return cursada.getCalificacionFinal() >= 6;})
          .map(Cursada::getCurso)
          .map(Curso::getMateria)
          .toList();

      if (validadorCorrelativas.puedeInscribirse(inscripcionRequest.getCurso().getMateria(), materiasAprobadas)) {
        EstadoInscripcion estadoInscripcion = new EstadoInscripcion(TipoEstadoInscripcion.PENDIENTE, LocalDateTime.now(), inscripcionRequest);
        inscripcionRepository.save(inscripcionRequest);
        return ResponseEntity.ok("Inscripción exitosa!");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No cumple con las correlativas necesarias");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/confirmar")
  public ResponseEntity<String> confirmarInscripcion(@RequestBody Inscripcion inscripcionRequest) {
    try {
      EstadoInscripcion estadoInscripcion = new EstadoInscripcion(TipoEstadoInscripcion.APROBADA, LocalDateTime.now(), inscripcionRequest);
      inscripcionRequest.setEstadoActual(estadoInscripcion);
      inscripcionRepository.save(inscripcionRequest);

      Mensaje mensaje = new Mensaje("Inscripcion", "Inscripcion confirmada en el curso " + inscripcionRequest.getCurso().getCodigo());
      Notificador.notificar(inscripcionRequest.getAlumno(), mensaje);

      return ResponseEntity.ok("Inscripción confirmada!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/rechazar")
  public ResponseEntity<String> rechazarInscripcion(@RequestBody Inscripcion inscripcionRequest) {
    try {
      EstadoInscripcion estadoInscripcion = new EstadoInscripcion(TipoEstadoInscripcion.RECHAZADA, LocalDateTime.now(), inscripcionRequest);
      inscripcionRequest.setEstadoActual(estadoInscripcion);
      inscripcionRepository.save(inscripcionRequest);

      Mensaje mensaje = new Mensaje("Inscripcion", "Inscripcion rechazada en el curso " + inscripcionRequest.getCurso().getCodigo());
      Notificador.notificar(inscripcionRequest.getAlumno(), mensaje);

      return ResponseEntity.ok("Inscripción rechazada!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
