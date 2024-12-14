package com.api.materias.model.entity;

import jakarta.persistence.*;

@Entity @Table
public class Alumno extends Persistente {
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private String nroDocumento;
    @Column @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
}
