FROM maven:3.8.7 as build
COPY . .
RUN mvn -B clean package -DskipTests

FROM openjdk:20
COPY --from=build ./target/*.jar EasyWalletApplication.jar
ENTRYPOINT ["java", "-jar","EasyWalletApplication.jar"]
EXPOSE 9065