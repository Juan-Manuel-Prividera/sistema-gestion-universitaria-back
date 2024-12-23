package com.api.materias.model.entity.curso;

import com.api.materias.model.entity.Persistente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity @Table(name = "estado_inscripcion")
@NoArgsConstructor
public class EstadoInscripcion extends Persistente {
  @Enumerated(EnumType.STRING)
  private TipoEstadoInscripcion estado;
  @Column
  private LocalDateTime fechaCambio;
  @OneToOne @JoinColumn(name = "inscripcion_id",referencedColumnName = "id")
  private Inscripcion inscripcion;
}
