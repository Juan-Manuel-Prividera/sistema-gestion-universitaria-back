package com.api.materias.controllers;

import com.api.materias.model.entity.Materia;
import com.api.materias.model.entity.PlanDeEstudios;
import com.api.materias.model.repository.PlanDeEstudiosRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<PlanDeEstudios> planes = planesRepository.findAll();
    for(PlanDeEstudios plan : planes){
      if (plan.getCodigo().equals(codigo)){
        return plan;
      }
    }
    return null;
  }

  @PostMapping("/planes")
  public void createPlanDeEstudios(@RequestBody PlanDeEstudios plan){
    planesRepository.save(plan);
  }

  @GetMapping("/plan/{codigo}/materias")
  public List<Materia> getPlanDeEstudiosMaterias(@PathVariable String codigo){
    List<PlanDeEstudios> planes = planesRepository.findAll();
    for(PlanDeEstudios plan : planes){
      if (plan.getCodigo().equals(codigo)){
        return plan.getMaterias();
      }
    }
    return null;
  }

  @GetMapping("/plan/{codigo}/materias/{nivel}")
  public List<Materia> getPlanDeEstudiosMateriasNivel(@PathVariable String codigo, @PathVariable Integer nivel){
    List<PlanDeEstudios> planes = planesRepository.findAll();
    for(PlanDeEstudios plan : planes){
      if (plan.getCodigo().equals(codigo)){
        return plan.getMaterias()
            .stream()
            .filter(materia -> materia.getNivel().equals(nivel))
            .toList();
      }
    }
    return null;
  }
}
