FROM maven:3.9.1-eclipse-temurin-11-alpine AS build

WORKDIR /app

COPY pom.xml .

RUN mvn -e -B dependency:resolve

COPY src ./src

RUN mvn -e -B clean package -DskipTests

FROM eclipse-temurin:11-alpine

EXPOSE 8080

COPY --from=build /app/target/disney-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]