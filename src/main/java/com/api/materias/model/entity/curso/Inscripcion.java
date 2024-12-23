package com.api.materias.model.entity.curso;

import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.personas.Alumno;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

import static org.hibernate.annotations.CascadeType.MERGE;
import static org.hibernate.annotations.CascadeType.PERSIST;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "inscripcion")
public class Inscripcion extends Persistente {
  @ManyToOne @JoinColumn(name = "alumno_id",referencedColumnName = "id")
  private Alumno alumno;
  @ManyToOne @JoinColumn(name = "curso_id",referencedColumnName = "id")
  private Curso curso;

  @Cascade({MERGE, PERSIST})
  @ManyToOne @JoinColumn(name = "estado_actual_id",referencedColumnName = "id")
  private EstadoInscripcion estadoActual;
  @Column
  private LocalDateTime fechaInscripcion;
}
