package com.api.materias;

import com.api.materias.utils.LogLevel;
import com.api.materias.utils.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class MateriasApplication {

    public static void main(String[] args) throws IOException {
      ConfigurableApplicationContext context = SpringApplication.run(MateriasApplication.class, args);
      Logger.configure("log/application.log", true);
      Logger.log(LogLevel.INFO, "Aplicación iniciada");
    }

}
