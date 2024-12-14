package com.api.materias;

import com.api.materias.model.entity.Materia;
import com.api.materias.model.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MateriasApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(MateriasApplication.class, args);
//        context.getBean(Init.class).init();
    }

}
