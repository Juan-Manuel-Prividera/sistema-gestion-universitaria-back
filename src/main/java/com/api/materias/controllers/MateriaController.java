package com.api.materias.controllers;


import com.api.materias.model.entity.curso.Materia;
import com.api.materias.model.repository.MateriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MateriaController {

  private final MateriaRepository materiaRepository;

  public MateriaController(MateriaRepository materiaRepository) {
    this.materiaRepository = materiaRepository;
  }

  @GetMapping("/materias")
  public List<Materia> getAllMaterias() {
    return materiaRepository.findAll();
  }

  @GetMapping("/materias/{id}")
  public Materia getMateria(@PathVariable Long id) {
    Optional<Materia> materia = materiaRepository.findById(id);
    return materia.orElse(null);
  }

  @PostMapping("/materias")
  public ResponseEntity<String> createMateria(@RequestBody Materia materiaRequest) {
    try {
      Materia materia = new Materia(materiaRequest);
      materiaRepository.save(materia);
      return ResponseEntity.ok().body("Materia cadastrado com sucesso!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/materia/{id}")
  public ResponseEntity<String> updateMateria(@PathVariable Long id, @RequestBody Materia materia) {
    Materia materiaAtual;
    if (materiaRepository.findById(id).isPresent()) {
      materiaAtual = materiaRepository.findById(id).get();
      materiaAtual.update(materia);
      materiaRepository.save(materiaAtual);
      return ResponseEntity.ok("Materia atualizada con exito!");
    } else
      return ResponseEntity.notFound().build();
  }


}
