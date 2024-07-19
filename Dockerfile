FROM maven:3.8.5-openjdk-17 AS build
COPY ../.. .
RUN mvn clean package -DskipTests

FROM openjdk:20
COPY --from=build ./target/*.jar EasyWalletApplication.jar
ENTRYPOINT ["java", "-jar","EasyWalletApplication.jar"]
EXPOSE 9065