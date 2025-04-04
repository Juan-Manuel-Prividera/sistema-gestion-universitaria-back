package com.api.materias.model.repository;

import com.api.materias.model.entity.curso.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
}
