FROM maven:3.8.5-openjdk-19 AS build
COPY ../.. .
RUN mvn clean package -DskipTests

FROM openjdk:19
COPY --from=build ./target/*.jar EasyWalletApplication.jar
ENTRYPOINT ["java", "-jar","EasyWalletApplication.jar"]
EXPOSE 9065