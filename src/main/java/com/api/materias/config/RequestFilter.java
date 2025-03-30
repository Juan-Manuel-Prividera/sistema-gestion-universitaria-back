
package com.api.materias.config;

import com.api.materias.utils.LogLevel;
import com.api.materias.utils.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@WebFilter("/*")
public class RequestFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // Loggear información de la solicitud
    Logger.log(LogLevel.TRACE, "Request recibida: " + request.getMethod() + " " + request.getRequestURL());
    try {
      // Continuar con la siguiente etapa del filtro (por ejemplo, el controlador)
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      // Loggear cualquier excepción que ocurra durante el procesamiento de la solicitud
      Logger.log(LogLevel.ERROR, "Error procesando la solicitud: " + e.getMessage());
      throw e;
    }
  }
}