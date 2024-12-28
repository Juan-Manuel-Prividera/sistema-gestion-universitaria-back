# Sistema Web de Gestion de Materias

## Caracteristicas principales
- Tecnologias: Java, Spring, Spring Boot, Spring Data JPA, Spring Security, Hibernate, MySQL
- Es un backend para una aplicacion web cliente pesado, que permita la gestion de cursos, materias, alumnos y docentes para universidades. Al estilo siu guarani
- Los alumnos y docentes pueden recibir notificaciones via email.
- Usuarios principales: Alumnos y Docentes


## Requerimientos funcionales

### Como alumno:
- Poder registrarse, indicando carrera y plan de estudios.
- Visualizar sus calificaciones de todas las materias que haya hecho o este haciendo.
- Visualizar que materias esta actualmente cursando.
- Visualizar que dia cursa que materia, en que horario, modalidad.
- Visualizar y modificar sus datos personales.
- Visualizar todas las materias de su plan de estudios y las correlatividades entre ellas.
- Visualizar promedios anuales y totales de sus notas.
- Permitir ordenar y filtrar las calificaciones las calificaciones.
- Permitir inscribirse a cursos.


### Como docente
- Poder registrarse.
- Visualizar que cursos tiene asignados.
- Crear evaluaciones.
- Visualizar que alumnos tiene en sus cursos.
- Asignar calificaciones a los alumnos de sus cursos.
- Visualizar estatidisticas sobre el resultado de calificaciones en cada curso.

### Como admin 
- Poder registrar alumnos y docentes.
- Poder asignar materias a los planes de estudio.
- Poder asignar docentes a los cursos.
- Poder cerrar las inscripciones a los cursos.
