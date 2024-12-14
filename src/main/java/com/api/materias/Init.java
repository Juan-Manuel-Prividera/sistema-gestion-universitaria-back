package com.api.materias;

import com.api.materias.model.entity.Carrera;
import com.api.materias.model.entity.Materia;
import com.api.materias.model.entity.PlanDeEstudios;
import com.api.materias.model.repository.MateriaRepository;
import com.api.materias.model.repository.PlanDeEstudiosRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Init {

  @Autowired
  MateriaRepository materiaRepository;
  @Autowired
  PlanDeEstudiosRepository planDeEstudiosRepository;

  @PostConstruct
  public void init() {


    Materia materia =  new Materia("Analisis Matematico I", null, 1);
    Materia materia1 =  new Materia("Algoritmos y Estructuras de Datos", null, 1);
    Materia materia11 =  new Materia("Sistemas y Organizaciones", null, 1);
    Materia materia2 =  new Materia("Matematica Discreta", null, 1);

    Materia materia3 = new Materia("Paradigmas de Programacion", List.of(materia1,materia2), 2);
    Materia materia31 = new Materia("Analisis de Sistemas", List.of(materia11), 2);

    Materia materia4 = new Materia("Disenio de sistemas", List.of(materia31,materia3), 3);

    PlanDeEstudios planDeEstudios = PlanDeEstudios.builder()
        .anioLanzado(2008)
        .codigo("K08")
        .materias(List.of(materia1,materia2,materia3,materia4,materia11,materia31,materia))
        .carrera(new Carrera("Ingenieria en Sistemas de Informacion", 5))
        .build();

    materiaRepository.save(materia);
    materiaRepository.save(materia1);
    materiaRepository.save(materia2);
    materiaRepository.save(materia11);
    materiaRepository.save(materia3);
    materiaRepository.save(materia31);
    materiaRepository.save(materia4);

    planDeEstudiosRepository.save(planDeEstudios);
  }
}
