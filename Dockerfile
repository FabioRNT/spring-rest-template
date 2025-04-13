# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

# Create app directory and set permissions
WORKDIR /app

# Copy build files first for layer caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build package
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

# Set workdir and user
WORKDIR /app
USER 1001

# Copy built jar from builder
COPY --from=builder --chown=1001:1001 /app/target/*.jar app.jar

# Run the application
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]