package com.api.materias.controllers;

import com.api.materias.model.entity.evaluaciones.Calificacion;
import com.api.materias.model.entity.evaluaciones.Evaluacion;
import com.api.materias.model.repository.CalificacionRepository;
import com.api.materias.model.repository.EvaluacionRepository;
import com.api.materias.service.CalculadorEstadisticasEvaluacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {
  private final EvaluacionRepository evaluacionRepository;
  private final CalificacionRepository calificacionRepository;
  private final CalculadorEstadisticasEvaluacion calculadorEstadisticasEvaluacion;

  public EvaluacionController(EvaluacionRepository evaluacionRepository, CalificacionRepository calificacionRepository,
                              CalculadorEstadisticasEvaluacion calculadorEstadisticasEvaluacion) {
    this.evaluacionRepository = evaluacionRepository;
    this.calificacionRepository = calificacionRepository;
    this.calculadorEstadisticasEvaluacion = calculadorEstadisticasEvaluacion;
  }

  @GetMapping("/curso/{idCurso}")
  public List<Evaluacion> getEvaluacionesPorCurso(@PathVariable Long idCurso) {
    return evaluacionRepository.findByCursoId(idCurso);
  }

  @GetMapping("/{idEvaluacion}")
  public Evaluacion getEvaluacionPorId(@PathVariable Long idEvaluacion) {
    return evaluacionRepository.findById(idEvaluacion).orElse(null);
  }

  @GetMapping("{idEvaluacion}/estadisticas")
  public Evaluacion getEstadisticasEvaluacion(@PathVariable Long idEvaluacion) {
    Evaluacion evaluacion = evaluacionRepository.findById(idEvaluacion).orElse(null);
    if (evaluacion.getProcentajeAprobacion() != null)
      return evaluacion;
    List<Calificacion> calificaciones = calificacionRepository.findByEvaluacionId(idEvaluacion);
    evaluacion = calculadorEstadisticasEvaluacion.calcularEstadisticas(evaluacion,calificaciones);
    evaluacionRepository.save(evaluacion);
    return evaluacion;
  }


  @PostMapping("/")
  public ResponseEntity<String> crearEvaluacion(@RequestPart Evaluacion evaluacion, @RequestPart MultipartFile archivo) {
    try {
      // Guardar el archivo en el sistema de archivos o en un almacenamiento externo
      String rutaDirectorio = "uploads/";
      File directorio = new File(rutaDirectorio);
      if (!directorio.exists()) {
        directorio.mkdirs();
      }
      String rutaArchivo = rutaDirectorio + archivo.getOriginalFilename();
      archivo.transferTo(new File(rutaArchivo));
      evaluacion.setRutaAlArchivo(rutaArchivo);

      evaluacionRepository.save(evaluacion);
      return ResponseEntity.ok("Evaluacion creada");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error al crear evaluacion");
    }
  }

}
