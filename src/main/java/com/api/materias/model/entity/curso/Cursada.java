package com.api.materias.model.entity.curso;

import com.api.materias.model.entity.Persistente;
import com.api.materias.model.entity.personas.Alumno;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Builder
@Entity @Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cursada extends Persistente {
    @ManyToOne
    private Curso curso;
    @ManyToOne
    private Alumno alumno;
    @Column
    private Integer nivel;
    @Column
    private LocalDate fechaInicio;
    @Column
    private LocalDate fechaFin;
    @Column
    private Boolean finalizado;
    @Column
    private Double calificacionFinal;


    public Boolean aprobo() {
        return calificacionFinal >= 6;
    }
    public Boolean promociono() {
        return calificacionFinal >= 8;
    }
}
