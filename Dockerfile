# Build stage
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn -B clean package -DskipTests

# Run stage
FROM openjdk:19
WORKDIR /app
COPY --from=build /app/target/*.jar EasyWalletApplication.jar
EXPOSE 9065
ENTRYPOINT ["java", "-jar", "EasyWalletApplication.jar"]
