package com.api.materias.converters;

import com.api.materias.model.MedioDeContactoFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

  @Bean
  public MedioContactoConverter medioContactoConverter(MedioDeContactoFactory factory) {
    return new MedioContactoConverter(factory);
  }
}