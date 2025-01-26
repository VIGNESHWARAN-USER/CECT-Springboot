# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]
