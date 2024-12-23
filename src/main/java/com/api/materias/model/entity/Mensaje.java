package com.api.materias.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mensaje {
  private String asunto;
  private String mensaje;
}
