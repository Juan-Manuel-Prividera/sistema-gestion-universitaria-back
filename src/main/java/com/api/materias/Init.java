package com.api.materias;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class Init {

//  @Autowired
//  MateriaRepository materiaRepository;
//  @Autowired
//  PlanDeEstudiosRepository planDeEstudiosRepository;

  @PostConstruct
  public void init() {

//
//    Materia am1 =  new Materia("Analisis Matematico I", null, 1);
//    Materia ayed =  new Materia("Algoritmos y Estructuras de Datos", null, 1);
//    Materia syo =  new Materia("Sistemas y Organizaciones", null, 1);
//    Materia md =  new Materia("Matematica Discreta", null, 1);
//    Materia aga =  new Materia("Algebra y Geometria Analitica", null, 1);
//    Materia qui = new Materia("Quimica", null, 1);
//    Materia ingsoc = new Materia("Ingenieria y Sociedad", null, 1);
//    Materia arq = new Materia("Arquitectura de Computadoras", null, 1);
//
//    Materia am2 = new Materia("Analisis Matematico II", List.of(am1,aga), 2);
//    Materia proba= new Materia("Probabilidad y Estadistica", List.of(am1,aga), 2);
//    Materia sintaxis = new Materia("Sintaxis y Semantica de los Lenguajes", List.of(ayed), 2);
//    Materia pdp = new Materia("Paradigmas de Programacion", List.of(ayed,md), 2);
//    Materia ads = new Materia("Analisis de Sistemas", List.of(syo), 2);
//    Materia sdr = new Materia("Sistemas de Representacion", null, 2);
//    Materia f1 = new Materia("Fisica I", null, 2);
//    Materia i1 = new Materia("Ingles Tecnico I", null, 2);
//
//
//    Materia dds = new Materia("Disenio de sistemas", List.of(pdp,ads), 3);
//    Materia so = new Materia("Sistemas Operativos", List.of(ayed,arq), 3);
//
//    PlanDeEstudios planDeEstudios = PlanDeEstudios.builder()
//        .anioLanzado(2008)
//        .codigo("K08")
//        .materias(List.of(am1,ayed,sdr,syo,md,aga,qui,ingsoc,arq,am2,proba,sintaxis,pdp,ads,f1,i1,dds,so))
//        .carrera(new Carrera("Ingenieria en Sistemas de Informacion", 5))
//        .build();
//
//    materiaRepository.save(am1);
//    materiaRepository.save(ayed);
//    materiaRepository.save(sdr);
//    materiaRepository.save(syo);
//    materiaRepository.save(md);
//    materiaRepository.save(aga);
//    materiaRepository.save(qui);
//    materiaRepository.save(ingsoc);
//    materiaRepository.save(arq);
//    materiaRepository.save(am2);
//    materiaRepository.save(proba);
//    materiaRepository.save(sintaxis);
//    materiaRepository.save(pdp);
//    materiaRepository.save(ads);
//    materiaRepository.save(f1);
//    materiaRepository.save(i1);
//    materiaRepository.save(dds);
//    materiaRepository.save(so);
//
//    planDeEstudiosRepository.save(planDeEstudios);
  }
}
