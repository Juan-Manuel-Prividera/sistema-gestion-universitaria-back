package com.api.materias.model.entity.personas;

import com.api.materias.model.entity.Mensaje;

public interface MedioContacto {

  void recibirNotificacion(String infoContacto, Mensaje mensaje);
}
