package com.api.materias.model.repository;

import com.api.materias.model.entity.usuarios.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
  Optional<Rol> findByRol(String rol);
}
