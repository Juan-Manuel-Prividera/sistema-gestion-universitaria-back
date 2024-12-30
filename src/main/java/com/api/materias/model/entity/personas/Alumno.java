package com.api.materias.model.entity.personas;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity @Table
@NoArgsConstructor
@AllArgsConstructor
public class Alumno extends Persistente implements ReceptorNotificaciones {
  @Column
  private String nombre;
  @Column
  private String apellido;
  @Column
  private Integer nroDocumento;
  @Column @Enumerated(EnumType.STRING)
  private TipoDocumento tipoDocumento;
  @Column
  private Integer legajo;

  @OneToOne @JoinColumn(name = "contacto_id",referencedColumnName = "id")
  private Contacto contacto;

  @OneToOne @JoinColumn(name = "usuario_id",referencedColumnName = "id")
  private Usuario usuario;


  public void update(Alumno alumno) {
    if (!Objects.equals(this.nombre, alumno.nombre)) {
      this.nombre = alumno.nombre;
    }
    if (!Objects.equals(this.apellido, alumno.apellido)) {
      this.apellido = alumno.apellido;
    }
    if (!Objects.equals(this.nroDocumento, alumno.nroDocumento)) {
      this.nroDocumento = alumno.nroDocumento;
    }
    if (this.tipoDocumento != alumno.tipoDocumento) {
      this.tipoDocumento = alumno.tipoDocumento;
    }
    if (!Objects.equals(this.legajo, alumno.legajo)) {
      this.legajo = alumno.legajo;
    }
  }


  @Override
  public void recibirNotificacion(Mensaje mensaje) {
    this.contacto.enviarNotificacion(mensaje);
  }
}
