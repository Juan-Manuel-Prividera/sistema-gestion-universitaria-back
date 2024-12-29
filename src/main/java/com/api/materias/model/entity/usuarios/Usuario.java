package com.api.materias.model.entity.usuarios;

import com.api.materias.model.entity.Persistente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity @Table(name = "usuarios")
public class Usuario extends Persistente {
  @Column(unique = true)
  private String username;
  @Column
  private String password;

  @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "rol_id", referencedColumnName = "id")
  private Rol rol;
}
