# Build stage
FROM maven:3.8.7-eclipse-temurin-19 AS build
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests

# Run stage
FROM eclipse-temurin:19-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar EasyWalletApplication.jar
EXPOSE 9065
ENTRYPOINT ["java", "-jar", "EasyWalletApplication.jar"]

