# ==============================================================================
# ETAPA DE CONSTRUCCIÓN (BUILD STAGE)
# Propósito: Compilar la aplicación Spring Boot y generar el JAR ejecutable.
# Utiliza una imagen con JDK 17 y Gradle preinstalados.
# ==============================================================================
FROM gradle:8-jdk17-jammy AS builder

# Define el directorio de trabajo dentro del contenedor para esta etapa.
WORKDIR /app

# Copia los archivos del wrapper de Gradle y los archivos de configuración
# Estos archivos cambian con menos frecuencia que el código fuente.
# Copiarlos primero permite a Docker cachear esta capa de manera eficiente.
COPY gradlew .
COPY gradle gradle/
COPY build.gradle.kts settings.gradle.kts ./
COPY src src/

# Compila el proyecto.
# -x test: Excluye la ejecución de los tests durante la construcción del JAR para acelerar.
# --no-daemon: Evita que Gradle cree un daemon persistente, ideal para entornos de CI/CD.
RUN gradle clean build -x test --no-daemon


# ==============================================================================
# ETAPA DE EJECUCIÓN (RUNNER STAGE)
# Propósito: Ejecutar la aplicación compilada.
# Utiliza una imagen JRE mínima para reducir drásticamente el tamaño y la superficie de ataque.
# ==============================================================================
FROM eclipse-temurin:17-jre-alpine

# Opcional pero ALTAMENTE RECOMENDADO para seguridad: Crear un usuario no-root.
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring

# Exponer el puerto que tu aplicación Spring Boot escucha (por defecto 8080).
EXPOSE 8080

# Establecemos un WORKDIR claro para esta etapa.
WORKDIR /app

# Copia el JAR compilado directamente desde la etapa 'builder' a la raíz de esta etapa.
# ¡Asegúrate de que el nombre del JAR sea EXACTAMENTE este!
COPY --from=builder /app/build/libs/user-microservice-0.0.1-SNAPSHOT.jar app.jar

# Tus variables de entorno (para estudio/desarrollo)
# ADVERTENCIA DE SEGURIDAD: NO USAR ESTO EN PRODUCCIÓN PARA CREDENCIALES SENSIBLES.
# Estos valores se incrustan en la capa de la imagen y son visibles.
ENV DB_PASSWORD=Pandequeso123
ENV DB_URL=jdbc:mysql://mysql_users:3306/users_db
ENV DB_USERNAME=mao
ENV JWT_EXPIRATION=100000000
ENV JWT_SECRET=f88982ab8e9ce6d6ebfc7fa3c33e9228136095d802e7e0cdf97c5c6269b3ffc8
ENV JWT_USER_GENERATOR=Mauricio-Backend
ENV PORT=8080

# Define el punto de entrada para iniciar la aplicación Spring Boot.
# En este caso, simplemente ejecuta el JAR.
ENTRYPOINT ["java", "-jar", "app.jar"]
