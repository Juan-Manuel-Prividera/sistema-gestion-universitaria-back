package com.api.materias.controllers;


import com.api.materias.model.entity.Materia;
import com.api.materias.model.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
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
  public void createMateria(@RequestBody Materia materiaRequest) {
    Materia materia = new Materia(materiaRequest);
    materiaRepository.save(materia);
  }

  @PostMapping("/materia/{id}")
  public void updateMateria(@PathVariable Long id, @RequestBody Materia materia) {
    Materia materiaAtual;
    if (materiaRepository.findById(id).isPresent()) {
      materiaAtual = materiaRepository.findById(id).get();
      materiaAtual.update(materia);
      materiaRepository.save(materiaAtual);
    }
    // TODO: Caso de error
  }

}
