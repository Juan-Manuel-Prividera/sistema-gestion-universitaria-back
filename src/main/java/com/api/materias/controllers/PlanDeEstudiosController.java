package com.api.materias.controllers;

import com.api.materias.model.entity.PlanDeEstudios;
import com.api.materias.model.entity.curso.Materia;
import com.api.materias.model.repository.PlanDeEstudiosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlanDeEstudiosController {

  private final PlanDeEstudiosRepository planesRepository;

  public PlanDeEstudiosController(PlanDeEstudiosRepository planesRepository) {
    this.planesRepository = planesRepository;
  }

  @GetMapping("/planes")
  public List<PlanDeEstudios> getAllPlanes(){
    return planesRepository.findAll();
  }

  @GetMapping("/plan/{codigo}")
  public PlanDeEstudios getPlanDeEstudios(@PathVariable String codigo){
    return planesRepository.findByCodigo(codigo);
  }

  @GetMapping("/plan/{codigo}/materias")
  public List<Materia> getPlanDeEstudiosMaterias(@PathVariable String codigo){
    return planesRepository.findByCodigo(codigo).getMaterias();
  }

  @GetMapping("/plan/{codigo}/materias/{nivel}")
  public List<Materia> getPlanDeEstudiosMateriasNivel(@PathVariable String codigo, @PathVariable Integer nivel){
    List<Materia> materias= planesRepository.findByCodigo(codigo).getMaterias();
    return materias.stream()
        .filter(materia -> materia.getNivel().equals(nivel))
        .toList();
  }

  @PostMapping("/planes")
  public ResponseEntity<String> createPlanDeEstudios(@RequestBody PlanDeEstudios plan){
    try {
      planesRepository.save(plan);
      return ResponseEntity.ok("Plan de estudios creado con exito!");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

}
