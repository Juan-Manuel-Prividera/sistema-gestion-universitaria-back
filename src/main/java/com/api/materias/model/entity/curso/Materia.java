package com.api.materias.model.entity.curso;

import com.api.materias.model.entity.Persistente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materia")
public class Materia extends Persistente {
    @Column
    private String nombre;
    @OneToMany
    private List<Materia> correlativas;
    @Column
    private Integer nivel; // Anio en el que se debe hacer

    public Materia(Materia materiaRequest) {
        super();
        this.nombre = materiaRequest.getNombre();
        this.nivel = materiaRequest.getNivel();
        this.correlativas = materiaRequest.getCorrelativas();
    }


    public void update(Materia materia) {
      if (!this.nombre.equals(materia.getNombre()))
        this.nombre = materia.getNombre();

      if (!this.nivel.equals(materia.getNivel()))
        this.nivel = materia.getNivel();

      for (Materia correlativa : materia.getCorrelativas())
        if (!this.correlativas.contains(correlativa))
          this.correlativas.add(correlativa);

      for (Materia correlativa : this.correlativas)
        if (!materia.getCorrelativas().contains(correlativa))
          this.correlativas.remove(correlativa);

    }

}
