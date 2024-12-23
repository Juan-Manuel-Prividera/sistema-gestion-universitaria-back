package com.api.materias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MateriasApplication {

    public static void main(String[] args) {
       ConfigurableApplicationContext context = SpringApplication.run(MateriasApplication.class, args);
    }

}
