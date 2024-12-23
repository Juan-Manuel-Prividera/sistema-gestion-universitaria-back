package com.api.materias.model.entity.evaluaciones;

import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.curso.Curso;
import com.api.materias.model.entity.curso.Instancia;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table
@Data
public class Evaluacion extends Persistente {
  @ManyToOne @JoinColumn(name = "curso_id", referencedColumnName = "id")
  private Curso curso;
  @Column
  private Double notaPromedio;
  @Column
  private Double notaMaxima;
  @Column
  private Double notaMinima;
  @Column
  private Double procentajeAprobacion;
  @Enumerated(EnumType.STRING)
  @Column
  private Instancia instancia;
  @Column
  private String rutaAlArchivo;
}
