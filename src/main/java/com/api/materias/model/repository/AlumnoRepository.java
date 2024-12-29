package com.api.materias.model.repository;

import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.entity.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
  Alumno findByUsuario(Usuario user);
}
