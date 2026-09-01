# Stage 1: Build the application using Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Package the app into a JAR file, skipping tests to speed up the build
RUN mvn clean package -DskipTests

# Stage 2: Create the lightweight production container
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copy only the compiled JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Tell Docker the container listens on port 8080
EXPOSE 8080
# Define the exact command to start the server
ENTRYPOINT ["java", "-jar", "app.jar"]