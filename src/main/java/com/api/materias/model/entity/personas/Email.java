package com.api.materias.model.entity.personas;

import com.api.materias.model.entity.Mensaje;
import com.api.materias.service.EmailSender;
import org.springframework.stereotype.Service;

@Service
public class Email implements MedioContacto{
  private final EmailSender emailSender;

  public Email(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void recibirNotificacion(String infoContacto, Mensaje mensaje) {
    emailSender.enviarCorreo(infoContacto, mensaje.getAsunto(), mensaje.getMensaje());
  }
}
