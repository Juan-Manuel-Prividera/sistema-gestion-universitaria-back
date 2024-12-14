package com.api.materias.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity @Table
public class Cursada extends Persistente{
    @ManyToOne
    private Materia materia;
    @ManyToOne
    private Alumno alumno;
    @Column
    private LocalDate fechaInicio;
    @Column
    private LocalDate fechaFin;
}
