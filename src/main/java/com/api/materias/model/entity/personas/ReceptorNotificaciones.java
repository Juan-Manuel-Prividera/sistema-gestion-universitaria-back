package com.api.materias.model.entity.personas;

import com.api.materias.model.entity.Mensaje;

public interface ReceptorNotificaciones {
  void recibirNotificacion(Mensaje mensaje);
}
