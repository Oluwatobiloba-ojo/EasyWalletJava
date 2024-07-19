FROM maven:3.8.7-openjdk-17 AS build
COPY . .
RUN mvn -B clean package -DskipTest

FROM openjdk:19
COPY --from=build ./target/*.jar EasyWalletApplication.jar
ENTRYPOINT ["java", "-jar","EasyWalletApplication.jar"]
EXPOSE 9065
