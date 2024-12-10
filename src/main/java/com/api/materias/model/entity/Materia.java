package com.api.materias.model.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class Materia {
    private PlanDeEstudios planDeEstudios;
    private String nombre;
    private List<Materia> correlativas;
    private int nivel; // Anio en el que se debe hacer
}
