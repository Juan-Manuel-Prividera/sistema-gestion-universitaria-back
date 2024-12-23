package com.api.materias.model.entity.personas;

import com.api.materias.converters.MedioContactoConverter;
import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.Persistente;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "contacto")
public class Contacto extends Persistente {
  @Column
  private String infoContacto;
  @Convert(converter = MedioContactoConverter.class)
  private MedioContacto medioContacto;

  public void enviarNotificacion(Mensaje mensaje) {
    medioContacto.recibirNotificacion(infoContacto, mensaje);
  }
}
