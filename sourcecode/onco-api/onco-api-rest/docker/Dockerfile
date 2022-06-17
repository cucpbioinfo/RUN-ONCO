# Use official base image of Java Runtim
FROM openjdk:8-jdk-slim-buster

RUN mkdir -p /app/data/downloads && \
  mkdir -p /app/data/uploads && \
  mkdir -p /app/logs

WORKDIR /deployments

# Set application's JAR file
ARG JAR_FILE=/onco-api.jar

# Add the application's JAR file to the container
ADD ${JAR_FILE} /deployments/app.jar

ENV JAVA_OPTS=""

# Run the JAR file
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /deployments/app.jar
