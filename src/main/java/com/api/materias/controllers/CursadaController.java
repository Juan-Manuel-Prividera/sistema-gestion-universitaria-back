package com.api.materias.controllers;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.curso.Cursada;
import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.repository.CalificacionRepository;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.service.Notificador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cursadas")
public class CursadaController {
  private final CursadaRepository cursadaRepository;
  private final CalificacionRepository calificacionRepository;

  public CursadaController(CursadaRepository cursadaRepository, CalificacionRepository calificacionRepository) {
    this.cursadaRepository = cursadaRepository;
    this.calificacionRepository = calificacionRepository;
  }

  @GetMapping("/alumno/{idAlumno}/nivel/{nivel}")
  public List<Cursada> getCursadasPorAlumnoPorNivel(@PathVariable Long idAlumno, @PathVariable Integer nivel) {
    return cursadaRepository.findByAlumnoIdAndNivel(idAlumno, nivel);
  }

  @GetMapping("/alumno/{idAlumno}")
  public List<Cursada> getCursadasPorAlumno(@PathVariable Long idAlumno) {
    return cursadaRepository.findByAlumnoId(idAlumno);
  }

  @GetMapping("/alumno/{idAlumno}/encursuo")
  public List<Cursada> getCursadasEnCursoPorAlumno(@PathVariable Long idAlumno) {
    return cursadaRepository.findByAlumnoIdAndFinalizado(idAlumno, true);
  }

  @GetMapping("/{idCursada}/calificaciones")
  public List<Calificacion> getCalificacionesPorCursadaPorAlumno(@PathVariable Long idCursada) {
    return calificacionRepository.findByCursadaId(idCursada);
  }


  @PostMapping("/")
  public ResponseEntity<String> createCursada(@RequestBody Cursada cursada) {
    try {
      cursadaRepository.save(cursada);
      return ResponseEntity.ok("Cursada creada con exito");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear la cursada");
    }
  }

  @PostMapping("/{idCursada}/calificacion/final")
  public ResponseEntity<String> setCalificacionFinal(@PathVariable Long idCursada, @RequestBody Double calificacionFinal) {
    Cursada cursada = cursadaRepository.findById(idCursada).orElse(null);
    if (cursada != null) {
      cursada.setCalificacionFinal(calificacionFinal);
      cursada.setFinalizado(true);
      cursada.setFechaFin(LocalDate.now());
      cursadaRepository.save(cursada);

      Mensaje mensaje = new Mensaje("Nota final", "Se cargo su nota final de la cursada del curso " + cursada.getCurso().getCodigo() + " con nota " + calificacionFinal);
      Notificador.notificar(cursada.getAlumno(), mensaje);
      return ResponseEntity.ok("Calificacion final guardada");
    } else {
      return ResponseEntity.badRequest().body("Cursada no encontrada");
    }
  }

}
