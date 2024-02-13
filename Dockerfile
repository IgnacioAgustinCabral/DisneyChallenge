FROM maven:3.8.7-eclipse-temurin-11-focal AS dependencies
WORKDIR /app
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

FROM dependencies AS build
COPY src ./src
RUN mvn -e -B clean package -DskipTests

FROM eclipse-temurin:11-jre-focal
WORKDIR /api
EXPOSE 8080
COPY --from=build /app/target/disney-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]