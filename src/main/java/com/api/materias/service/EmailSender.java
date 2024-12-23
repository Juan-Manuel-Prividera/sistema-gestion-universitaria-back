package com.api.materias.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
  private final JavaMailSender mailSender;

  public EmailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void enviarCorreo(String para, String asunto, String mensaje) {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(para);
    email.setSubject(asunto);
    email.setText(mensaje);
    mailSender.send(email);
  }
}
