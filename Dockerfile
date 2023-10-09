FROM maven:3.9.1-eclipse-temurin-11-alpine

EXPOSE 8080

COPY target/disney-0.0.1-SNAPSHOT.jar  app.jar

ENTRYPOINT ["java","-jar","app.jar"]