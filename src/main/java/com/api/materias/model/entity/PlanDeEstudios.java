package com.api.materias.model.entity;

import com.api.materias.model.entity.curso.Materia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PlanDeEstudios extends Persistente{
  @Column
  private Integer anioLanzado;
  @Column
  private String codigo;

  @ManyToOne @Cascade({org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST})
  @JoinColumn(name = "carrera_id", referencedColumnName = "id")
  private Carrera carrera;

  @ManyToMany
  private List<Materia> materias;

}
