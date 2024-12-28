package com.api.materias.model.entity.personas;


import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "docente")
public class Docente extends Persistente implements ReceptorNotificaciones{
  @Column
  private String nombre;
  @Column
  private String apellido;
  @OneToOne @JoinColumn(name = "contacto_id", referencedColumnName = "id")
  private Contacto contacto;
  @Column
  private Integer edad;
  @Column
  private LocalDate fechaIngresoDocente;

  @OneToOne @JoinColumn(name = "usuario_id",referencedColumnName = "id")
  private Usuario usuario;

  public void update(Docente docente) {
    if (!Objects.equals(this.nombre, docente.nombre)) {
      this.nombre = docente.nombre;
    }
    if (!Objects.equals(this.apellido, docente.apellido)) {
      this.apellido = docente.apellido;
    }
    if (!Objects.equals(this.contacto, docente.contacto)) {
      this.contacto = docente.contacto;
    }
    if (!Objects.equals(this.edad, docente.edad)) {
      this.edad = docente.edad;
    }
    if (this.fechaIngresoDocente != docente.fechaIngresoDocente) {
      this.fechaIngresoDocente = docente.fechaIngresoDocente;
    }

  }

  @Override
  public void recibirNotificacion(Mensaje mensaje) {
    contacto.enviarNotificacion(mensaje);
  }
}
