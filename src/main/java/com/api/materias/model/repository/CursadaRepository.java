package com.api.materias.model.repository;


import com.api.materias.model.entity.curso.Cursada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursadaRepository extends JpaRepository<Cursada, Long> {
  List<Cursada> findByAlumnoIdAndNivel(Long idAlumno, Integer nivel);

  List<Cursada> findByAlumnoId(Long idAlumno);

  List<Cursada> findByAlumnoIdAndFinalizado(Long idAlumno, boolean b);

  Long id(Long id);
}
