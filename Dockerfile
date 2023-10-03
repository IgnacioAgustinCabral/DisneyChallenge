#FROM maven:3.9.1-eclipse-temurin-11-alpine
#
#COPY target/disney-0.0.1-SNAPSHOT.jar disney-0.0.1-SNAPSHOT.jar
#
#ENTRYPOINT ["java","-jar","/disney-0.0.1-SNAPSHOT.jar"]


# Use a base image with Java and Maven
FROM maven:3.9.1-eclipse-temurin-11-alpine

# Set the working directory in the container
WORKDIR /app

# Copy only the Maven POM file to cache dependencies
COPY pom.xml .

# Download the project dependencies. This layer will be cached
# unless the pom.xml file changes
RUN mvn dependency:go-offline

# Copy the rest of the application code to the container
COPY src ./src

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Run your Spring Boot application with hot-reloading
CMD ["mvn", "spring-boot:run"]