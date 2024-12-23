package com.api.materias.converters;

import com.api.materias.model.MedioDeContactoFactory;
import com.api.materias.model.entity.personas.Email;
import com.api.materias.model.entity.personas.MedioContacto;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MedioContactoConverter implements AttributeConverter<MedioContacto, String> {
  private final MedioDeContactoFactory medioDeContactoFactory;

  public MedioContactoConverter(MedioDeContactoFactory medioDeContactoFactory) {
    this.medioDeContactoFactory = medioDeContactoFactory;
  }

  @Override
  public String convertToDatabaseColumn(MedioContacto medioContacto) {
    if (medioContacto instanceof Email email) return "Email";
    else return null;
  }

  @Override
  public MedioContacto convertToEntityAttribute(String s) {
    return medioDeContactoFactory.createMedioContacto(s);
  }
}
