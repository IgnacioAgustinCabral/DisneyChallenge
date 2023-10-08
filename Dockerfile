FROM maven:3.9.1-eclipse-temurin-11-alpine

EXPOSE 8080

ADD target/disney-0.0.1-SNAPSHOT.jar disney-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/disney-0.0.1-SNAPSHOT.jar"]