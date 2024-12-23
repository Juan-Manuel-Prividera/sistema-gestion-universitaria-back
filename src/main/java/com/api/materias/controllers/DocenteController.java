package com.api.materias.controllers;

import com.api.materias.model.entity.personas.Docente;
import com.api.materias.model.repository.CursoRepository;
import com.api.materias.model.repository.DocenteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocenteController {
  private final DocenteRepository docenteRepository;
  private final CursoRepository cursoRepository;

  public DocenteController(DocenteRepository docenteRepository, CursoRepository cursoRepository) {
    this.docenteRepository = docenteRepository;
    this.cursoRepository = cursoRepository;
  }

  @PostMapping("/docente")
  public ResponseEntity<String> crearDocente(@RequestBody Docente docente) {
    try {
      docenteRepository.save(docente);
      return ResponseEntity.ok("Docente creado");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear docente");
    }
  }

  @PostMapping("/docentes/{idDocente}")
  public ResponseEntity<String> actualizarDocente(@PathVariable Long idDocente, @RequestBody Docente docente) {
    try {
      Docente docenteActual = docenteRepository.findById(idDocente).orElse(null);
      if (docenteActual == null) {
        return ResponseEntity.badRequest().body("Docente no encontrado");
      }
      docenteActual.update(docente);
      docenteRepository.save(docenteActual);
      return ResponseEntity.ok("Docente actualizado");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al actualizar docente");
    }
  }
}