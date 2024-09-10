# Usa una imagen base con JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Spring Boot en el contenedor
COPY target/*.jar quinteropo-bank-0.0.1-SNAPSHOT.jar

# Expone el puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "quinteropo-bank-0.0.1-SNAPSHOT.jar"]
