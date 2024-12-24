package com.api.materias.controllers;


import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.curso.*;
import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.model.repository.InscripcionRepository;
import com.api.materias.service.Notificador;
import com.api.materias.service.SelectorAlumnosAInscribir;
import com.api.materias.service.ValidadorCorrelativas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {
  private final ValidadorCorrelativas validadorCorrelativas;
  private final SelectorAlumnosAInscribir selectorAlumnosAInscribir;
  private final InscripcionRepository inscripcionRepository;
  private final CursadaRepository cursadaRepository;

  public InscripcionController(ValidadorCorrelativas validadorCorrelativas, SelectorAlumnosAInscribir selectorAlumnosAInscribir, InscripcionRepository inscripcionRepository, CursadaRepository cursadaRepository) {
    this.validadorCorrelativas = validadorCorrelativas;
    this.selectorAlumnosAInscribir = selectorAlumnosAInscribir;
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



  @PostMapping("curso/{idCurso}/cerrar")
  public ResponseEntity<String> cerrarInscripciones(@PathVariable Long idCurso) {
    try {
      Curso curso = inscripcionRepository.findByCursoId(idCurso).get(0).getCurso();
      List<Inscripcion> inscripciones = inscripcionRepository.findByCursoId(idCurso);
      List<Alumno> alumnosInscriptos = inscripciones.stream().map(Inscripcion::getAlumno).toList();

      if (alumnosInscriptos.size() <= curso.getCupo()) {
        // Entran todos los inscriptos
        aceptarInscripciones(inscripciones);

      } else {
        List<Alumno> alumnosOrdenados = selectorAlumnosAInscribir.ordernarAlumnos(alumnosInscriptos);
        List<Inscripcion> inscripcionesAceptadas = inscripciones.stream()
            .filter(inscripcion -> {return alumnosOrdenados.subList(0, curso.getCupo()).contains(inscripcion.getAlumno());}).toList();
        List<Inscripcion> inscripcionesRechazadas = inscripciones.stream()
            .filter(inscripcion -> {return !alumnosOrdenados.subList(0, curso.getCupo()).contains(inscripcion.getAlumno());}).toList();

        aceptarInscripciones(inscripcionesAceptadas);
        rechazarInscripciones(inscripcionesRechazadas);
      }
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
    return ResponseEntity.ok("Inscripciones cerradas");
  }


  private void aceptarInscripciones(List<Inscripcion> inscripciones) {
    inscripciones.forEach(inscripcion -> {
      inscripcion.setEstadoActual(new EstadoInscripcion(TipoEstadoInscripcion.APROBADA, LocalDateTime.now(), inscripcion));

      Mensaje mensaje = new Mensaje("Inscripcion", "Inscripcion confirmada en el curso " + inscripcion.getCurso().getCodigo());
      Notificador.notificar(inscripcion.getAlumno(), mensaje);

      inscripcionRepository.save(inscripcion);
    });
  }

  private void rechazarInscripciones(List<Inscripcion> inscripciones) {
    inscripciones.forEach(inscripcion -> {
      inscripcion.setEstadoActual(new EstadoInscripcion(TipoEstadoInscripcion.RECHAZADA, LocalDateTime.now(), inscripcion));

      Mensaje mensaje = new Mensaje("Inscripcion", "Inscripcion rechazada en el curso " + inscripcion.getCurso().getCodigo());
      Notificador.notificar(inscripcion.getAlumno(), mensaje);

      inscripcionRepository.save(inscripcion);
    });
  }

}
