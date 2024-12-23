package com.api.materias.model.repository;

import com.api.materias.model.entity.curso.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
  List<Inscripcion> findByCursoId(Long idCurso);

}
