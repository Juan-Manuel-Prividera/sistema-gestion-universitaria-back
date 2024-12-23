package com.api.materias.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class Persistente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Boolean activo;
    @Column(nullable = false)
    private LocalDateTime fechaAlta;

    public Persistente() {
        this.activo = true;
        this.fechaAlta = LocalDateTime.now();
    }
}
