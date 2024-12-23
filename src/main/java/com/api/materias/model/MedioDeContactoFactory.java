package com.api.materias.model;

import com.api.materias.model.entity.personas.Email;
import com.api.materias.model.entity.personas.MedioContacto;
import com.api.materias.service.EmailSender;
import org.springframework.stereotype.Component;

@Component
public class MedioDeContactoFactory {
  private final EmailSender emailSender;

  public MedioDeContactoFactory(EmailSender emailSender) {
    this.emailSender = emailSender;
  }

  public MedioContacto createMedioContacto(String type) {
    if ("Email".equals(type)) {
      return new Email(emailSender);
    }
    return null;
  }
}
