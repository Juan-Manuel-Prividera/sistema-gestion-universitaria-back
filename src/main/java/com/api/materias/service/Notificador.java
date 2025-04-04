package com.api.materias.service;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.model.entity.personas.ReceptorNotificaciones;
import org.springframework.stereotype.Service;

@Service
public class Notificador {
  public static void notificar(ReceptorNotificaciones receptor, Mensaje mensaje) {
    receptor.recibirNotificacion(mensaje);
  }
}

