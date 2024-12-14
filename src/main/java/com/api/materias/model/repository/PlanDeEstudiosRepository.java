package com.api.materias.model.repository;

import com.api.materias.model.entity.PlanDeEstudios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanDeEstudiosRepository extends JpaRepository<PlanDeEstudios, Long> {
}
