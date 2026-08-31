FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# Копируем всё содержимое проекта
COPY . .

# Собираем JAR без очистки и с ключом компиляции
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
