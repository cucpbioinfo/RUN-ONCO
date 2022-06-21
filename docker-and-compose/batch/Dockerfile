# Use official base image of Java Runtim
FROM openjdk:8-jdk-slim

# RUN apk add --update \
#     curl \
#     && rm -rf /var/cache/apk/*

# Set up the Environment Variable for Application
ENV APP_HOME=/app

# Setting up the Work directory
WORKDIR ${APP_HOME}

RUN mkdir -p ${APP_HOME}/data/downloads && \
  mkdir -p ${APP_HOME}data/uploads && \
  mkdir -p ${APP_HOME}/logs

# Set application's JAR file
ARG JAR_FILE=/batch-api.jar

# Copy the application's JAR file to the container
COPY ${JAR_FILE} /app.jar

# Run the JAR file
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -jar /app.jar