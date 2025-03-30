FROM eclipse-temurin:21-jdk-alpine

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR de nuestra aplicación al contenedor
COPY target/materias-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que corre la app (por defecto 8080 en Spring Boot)
EXPOSE 8080

# Ejecutamos la aplicación
CMD ["java", "-jar", "app.jar"]