package com.api.materias.controllers;

import com.api.materias.model.entity.curso.Curso;
import com.api.materias.model.repository.CursoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
  private final CursoRepository cursoRepository;

  public CursoController(CursoRepository cursoRepository) {
    this.cursoRepository = cursoRepository;
  }

  @PreAuthorize("hasRole('ROLE_DOCENTE') or hasRole('ROLE_ADMIN')")
  @GetMapping("/{idCurso}")
  public Curso getCurusoPorId(@PathVariable Long idCurso) {
    return cursoRepository.findById(idCurso).orElse(null);
  }

  @PreAuthorize("hasRole('ROLE_DOCENTE') or hasRole('ROLE_ADMIN')")
  @GetMapping("/docente/{idDocente}")
  public List<Curso> getCursosPorDocente(@PathVariable Long idDocente) {
    return cursoRepository.findAllByDocenteId(idDocente);
  }


  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/")
  public ResponseEntity<String> crearCurso(@RequestBody Curso curso) {
    try {
      cursoRepository.save(curso);
      return ResponseEntity.ok("Curso creado con exito");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear el curso");
    }
  }

}

