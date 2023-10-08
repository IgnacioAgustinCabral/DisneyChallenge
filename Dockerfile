FROM maven:3.9.1-eclipse-temurin-11-alpine

EXPOSE 8080

ARG JAR_FILE=target/disney-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]