package com.api.materias.model.entity.usuarios;

import com.api.materias.model.entity.Persistente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "roles")
public class Rol extends Persistente {
  @Column(unique = true)
  private String rol;
  @ManyToMany @JoinTable(
    name = "roles_permisos",
    joinColumns = @JoinColumn(name = "rol_id"),
    inverseJoinColumns = @JoinColumn(name = "permiso_id")
  )
  private List<Permiso> permisos;

  public Boolean tienePermiso(Permiso permiso) {
    return permisos.contains(permiso);
  }
}
