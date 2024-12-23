package com.api.materias.model.entity.evaluaciones;

import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.curso.Cursada;
import com.api.materias.model.entity.personas.Alumno;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table
@Data
public class Calificacion extends Persistente {
  @Column
  private Double nota;
  @Column
  private LocalDate fechaCalificado;
  @ManyToOne @JoinColumn(name = "evaluacion_id", referencedColumnName = "id")
  private Evaluacion evaluacion;
  @ManyToOne @JoinColumn(name = "alumno_id", referencedColumnName = "id")
  private Alumno alumno;
  @ManyToOne @JoinColumn(name = "cursada_id", referencedColumnName = "id")
  private Cursada cursada;

  public Boolean estaAprobado() {
    return nota >= 6;
  }
  public Boolean estaPromocionado() {
    return nota >= 8;
  }
}
