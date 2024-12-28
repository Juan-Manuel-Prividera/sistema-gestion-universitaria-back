package com.api.materias.model.entity.usuarios;

import com.api.materias.model.entity.Persistente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "permisos")
public class Permiso extends Persistente {
  @Column(unique = true)
  private String permiso;
}
