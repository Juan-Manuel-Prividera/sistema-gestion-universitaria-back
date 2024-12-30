package com.api.materias;

import com.api.materias.model.entity.curso.Cursada;
import com.api.materias.model.entity.personas.Alumno;
import com.api.materias.model.entity.personas.TipoDocumento;
import com.api.materias.model.repository.CursadaRepository;
import com.api.materias.service.SelectorAlumnosAInscribir;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.beans.Customizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class SelectorAlumnosTest {
  Alumno mejorAlumno = Alumno.builder()
      .nombre("Juan")
      .build();


  Alumno alumnoIntermedio = Alumno.builder()
      .nombre("Pedro")
      .build();
  Alumno peorAlumno = Alumno.builder()
      .nombre("Pablo")
      .build();

  Cursada cursadaM1 = Cursada.builder()
      .alumno(mejorAlumno)
      .calificacionFinal(10.0)
      .build();

  Cursada cursadaM2 = Cursada.builder()
      .alumno(mejorAlumno)
      .calificacionFinal(10.0)
      .build();

  Cursada cursadaM3 = Cursada.builder()
      .alumno(mejorAlumno)
      .calificacionFinal(10.0)
      .build();

  Cursada cursadaI1 = Cursada.builder()
      .alumno(alumnoIntermedio)
      .calificacionFinal(8.0)
      .build();

  Cursada cursadaI2 = Cursada.builder()
      .alumno(alumnoIntermedio)
      .calificacionFinal(8.0)
      .build();

  Cursada cursadaP1 = Cursada.builder()
      .alumno(peorAlumno)
      .calificacionFinal(6.0)
      .build();

  List<Cursada> cursadasMejor;
  List<Cursada> cursadasIntermedio;
  List<Cursada> cursadasPeor;

  CursadaRepository cursadaRepositoryMock;

  SelectorAlumnosAInscribir selectorAlumnosAInscribir;

  @BeforeEach
  public void init() {
    mejorAlumno.setId(1L);
    alumnoIntermedio.setId(2L);
    peorAlumno.setId(3L);
    mejorAlumno.setFechaAlta(LocalDate.now().minusYears(1).atStartOfDay());
    alumnoIntermedio.setFechaAlta(LocalDate.now().minusYears(1).atStartOfDay());
    peorAlumno.setFechaAlta(LocalDate.now().minusYears(1).atStartOfDay());

    cursadaRepositoryMock = Mockito.mock(CursadaRepository.class);

    cursadasMejor = List.of(cursadaM1, cursadaM2, cursadaM3);
    cursadasIntermedio = List.of(cursadaI1, cursadaI2);
    cursadasPeor = List.of(cursadaP1);

    when(cursadaRepositoryMock.findByAlumnoId(mejorAlumno.getId())).thenReturn(cursadasMejor);
    when(cursadaRepositoryMock.findByAlumnoId(alumnoIntermedio.getId())).thenReturn(cursadasIntermedio);
    when(cursadaRepositoryMock.findByAlumnoId(peorAlumno.getId())).thenReturn(cursadasPeor);

    selectorAlumnosAInscribir = new SelectorAlumnosAInscribir(cursadaRepositoryMock);
  }

  @Test @DisplayName("Se ordenan los alumnos segun su peso academico")
  public void ordenarAlumnosTest() {
    List<Alumno> alumnos = new ArrayList<>(List.of(mejorAlumno, alumnoIntermedio, peorAlumno));

    List<Alumno> alumnosOrdenados = selectorAlumnosAInscribir.ordernarAlumnos(alumnos);

    Assertions.assertEquals(alumnosOrdenados.get(0), mejorAlumno);
    Assertions.assertEquals(alumnosOrdenados.get(1), alumnoIntermedio);
    Assertions.assertEquals(alumnosOrdenados.get(2), peorAlumno);
  }

  @Test @DisplayName("Se calcula el peso academico de un alumno")
  public void calcularPesoAcademicoTest() {
    int pesoAcademicoMejor = selectorAlumnosAInscribir.calcularPesoAcademico(mejorAlumno);
    int pesoAcademicoIntermedio = selectorAlumnosAInscribir.calcularPesoAcademico(alumnoIntermedio);
    int pesoAcademicoPeor = selectorAlumnosAInscribir.calcularPesoAcademico(peorAlumno);

    Assertions.assertEquals(11 * 3 - 5, pesoAcademicoMejor);
    Assertions.assertEquals(11 * 2 - 5, pesoAcademicoIntermedio);
    Assertions.assertEquals(11-5, pesoAcademicoPeor);
  }
}
