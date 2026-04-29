# Stage 1: Сборка приложения с помощью Maven [cite: 43]
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Запуск оптимизированного образа [cite: 44]
FROM bellsoft/liberica-openjdk-alpine:17
WORKDIR /app
# Копируем только готовый jar-файл из первого этапа
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]