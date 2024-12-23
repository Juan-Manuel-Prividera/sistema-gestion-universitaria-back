package com.api.materias.model.repository;

import com.api.materias.model.entity.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
  List<Curso> findAllByDocenteId(Long idDocente);
}
