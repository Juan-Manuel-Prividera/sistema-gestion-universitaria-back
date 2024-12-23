package com.api.materias.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Carrera  extends Persistente{
  @Column
  private String nombre;
  @Column
  private Integer duracion; // anios
  @Column
  private Character letraIdentificadora; // K, Q, R ...
}
