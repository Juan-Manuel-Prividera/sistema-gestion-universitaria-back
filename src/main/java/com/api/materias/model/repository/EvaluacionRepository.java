package com.api.materias.model.repository;

import com.api.materias.model.entity.evaluaciones.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
  List<Evaluacion> findByCursoId(Long idCurso);
}
