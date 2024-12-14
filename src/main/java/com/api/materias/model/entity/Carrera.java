package com.api.materias.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import jdk.jfr.Enabled;
import lombok.*;

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

}
