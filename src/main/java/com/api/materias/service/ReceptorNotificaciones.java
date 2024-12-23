package com.api.materias.service;

import com.api.materias.model.entity.Mensaje;

public interface ReceptorNotificaciones {
  void recibirNotificacion(Mensaje mensaje);
}
