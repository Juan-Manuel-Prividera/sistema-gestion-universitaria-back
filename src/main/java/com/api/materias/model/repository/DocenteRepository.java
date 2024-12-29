package com.api.materias.model.repository;

import com.api.materias.model.entity.personas.Docente;
import com.api.materias.model.entity.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
  Docente findByUsuario(Usuario usuario);
}
