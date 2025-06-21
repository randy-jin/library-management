# Dockerfile
FROM amazoncorretto:17-alpine AS builder

# Set working directory
WORKDIR /app

# Copy maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Make the Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build application
RUN ./mvnw clean package -DskipTests

# Create final image
FROM amazoncorretto:17-alpine

WORKDIR /app

# Install curl for health checks
    RUN apk add --no-cache curl

# Copy jar file from build stage
COPY --from=builder /app/target/library-management-*.jar app.jar

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]