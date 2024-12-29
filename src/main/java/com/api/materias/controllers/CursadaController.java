package com.api.materias.controllers;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.curso.Cursada;
import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.entity.usuarios.Usuario;
import com.api.materias.model.repository.AlumnoRepository;
import com.api.materias.model.repository.CalificacionRepository;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.model.repository.UsuarioRepository;
import com.api.materias.service.Notificador;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/cursadas")
public class CursadaController {
  private final CursadaRepository cursadaRepository;
  private final CalificacionRepository calificacionRepository;
  private final UsuarioRepository usuarioRepository;
  private final AlumnoRepository alumnoRepository;

  public CursadaController(CursadaRepository cursadaRepository, CalificacionRepository calificacionRepository, UsuarioRepository usuarioRepository, AlumnoRepository alumnoRepository) {
    this.cursadaRepository = cursadaRepository;
    this.calificacionRepository = calificacionRepository;
    this.usuarioRepository = usuarioRepository;
    this.alumnoRepository = alumnoRepository;
  }

  // Validamos que el idAlumno sea el mismo que el alumno logueado
  @PreAuthorize("hasRole('ROLE_ALUMNO') and #idAlumno == principal.idAlumno")
  @GetMapping("/alumno/{idAlumno}/nivel/{nivel}")
  public List<Cursada> getCursadasPorAlumnoPorNivel(@PathVariable Long idAlumno, @PathVariable Integer nivel) {
    // Validamos que el Alumno logueado sea el mismo que el que se esta consultando
    return cursadaRepository.findByAlumnoIdAndNivel(idAlumno, nivel);
  }

  @PreAuthorize("(hasRole('ROLE_ALUMNO') and #idAlumno == principal.idAlumno) or hasRole('ROLE_ADMIN')")
  @GetMapping("/alumno/{idAlumno}")
  public List<Cursada> getCursadasPorAlumno(@PathVariable Long idAlumno) {
    return cursadaRepository.findByAlumnoId(idAlumno);
  }

  @PreAuthorize("hasRole('ROLE_ALUMNO') and #idAlumno == principal.idAlumno")
  @GetMapping("/alumno/{idAlumno}/encursuo")
  public List<Cursada> getCursadasEnCursoPorAlumno(@PathVariable Long idAlumno) {
    return cursadaRepository.findByAlumnoIdAndFinalizado(idAlumno, true);
  }

  @PreAuthorize("hasRole('ROLE_ALUMNO') and #idAlumno == principal.idAlumno")
  @GetMapping("/{idCursada}/calificaciones")
  public List<Calificacion> getCalificacionesPorCursadaPorAlumno(@PathVariable Long idCursada) {
    return calificacionRepository.findByCursadaId(idCursada);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/")
  public ResponseEntity<String> createCursada(@RequestBody Cursada cursada) {
    try {
      cursadaRepository.save(cursada);
      return ResponseEntity.ok("Cursada creada con exito");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear la cursada");
    }
  }

  @PreAuthorize("hasRole('ROLE_DOCENTE') and hasAuthority('PERM_CALIFICAR')")
  @PostMapping("/{idCursada}/calificacion/final")
  public ResponseEntity<String> setCalificacionFinal(@PathVariable Long idCursada, @RequestBody Double calificacionFinal) {
    // TODO: Validar que el docente sea quien dicto ese curso
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
