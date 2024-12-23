package com.api.materias.model.entity.curso;


import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.personas.Docente;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "curso")
public class Curso extends Persistente {
  @Column
  private String codigo; // K2001
  @ManyToOne @JoinColumn(name = "docente_id", referencedColumnName = "id")
  private Docente docente;
  @ManyToOne @JoinColumn(name = "materia_id", referencedColumnName = "id")
  private Materia materia;
  @Column(name = "ciclo_lectivo")
  private Integer cilcoLectivo;// Anio en el que se dicta
  @Column
  private Integer cupo;
  @Column
  @Enumerated(EnumType.STRING)
  private Turno turno;
  @Column
  @Enumerated(EnumType.STRING)
  private Modalidad modalidad;
}
