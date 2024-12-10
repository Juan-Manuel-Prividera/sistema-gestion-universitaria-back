package com.api.materias.model.repository;


import com.api.materias.model.entity.Cursada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursadaRepository extends JpaRepository<Cursada, Long> {
}
