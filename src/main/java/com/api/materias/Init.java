package com.api.materias;

import com.api.materias.model.entity.curso.Materia;
import com.api.materias.model.entity.usuarios.Permiso;
import com.api.materias.model.entity.usuarios.Rol;
import com.api.materias.model.repository.MateriaRepository;
import com.api.materias.model.repository.PermisoRepository;
import com.api.materias.model.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Init {

  private final RolRepository rolRepository;
  private final MateriaRepository materiaRepository;
  private final PermisoRepository permisoRepository;

  public Init(RolRepository rolRepository, MateriaRepository materiaRepository, PermisoRepository permisoRepository) {
    this.rolRepository = rolRepository;
    this.materiaRepository = materiaRepository;
    this.permisoRepository = permisoRepository;
  }

  @PostConstruct
  public void init() {
    Permiso getMaterias;
    if (permisoRepository.findByPermiso("GET_MATERIAS").isEmpty()) {
      getMaterias = new Permiso("GET_MATERIAS");
      permisoRepository.save(getMaterias);
    } else {
      getMaterias = permisoRepository.findByPermiso("GET_MATERIAS").get();
    }

    if (rolRepository.findByRol("ADMIN").isEmpty()) {
      rolRepository.save(new Rol("ADMIN", List.of(getMaterias)));
    }
    if (rolRepository.findByRol("ALUMNO").isEmpty()) {
      rolRepository.save(new Rol("ALUMNO", null));
    }
    if (rolRepository.findByRol("DOCENTE").isEmpty()) {
      rolRepository.save(new Rol("DOCENTE", null));
    }
  }

  @PostConstruct
  public void initMaterias() {
    Materia am1 = new Materia("Análisis Matemático 1", null, 1);
    Materia aga = new Materia("Álgebra y Geometría Analítica", null, 1);
    Materia quimica = new Materia("Química", null, 1);
    Materia md = new Materia("Matemática Discreta", null, 1);
    Materia syo = new Materia("Sistemas y Organizaciones", null, 1);
    Materia am2 = new Materia("Análisis Matemático 2", List.of(am1,aga), 2);
    Materia ayed = new Materia("Algoritmos y Estructuras de Datos", null, 1);

    materiaRepository.save(am1);
    materiaRepository.save(aga);
    materiaRepository.save(quimica);
    materiaRepository.save(md);
    materiaRepository.save(syo);
    materiaRepository.save(am2);
    materiaRepository.save(ayed);
  }
}
