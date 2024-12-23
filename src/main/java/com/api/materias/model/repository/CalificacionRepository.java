package com.api.materias.model.repository;

import com.api.materias.model.entity.evaluaciones.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

  List<Calificacion> findByAlumnoId(Long idAlumno);

  List<Calificacion> findByCursadaId(Long idCursada);

  List<Calificacion> findByEvaluacionId(Long idEvaluacion);
}
