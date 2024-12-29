package com.api.materias.model.repository;

import com.api.materias.model.entity.usuarios.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
  Optional<Permiso> findByPermiso(String permiso);
}
