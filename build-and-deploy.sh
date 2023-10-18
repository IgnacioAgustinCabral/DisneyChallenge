#!/bin/bash

echo "Cleaning up dangling images..."
docker image prune -f

# Run Maven clean and package
echo "Building the application with Maven..."
mvn clean package

# Check if the Maven build was successful
if [ $? -eq 0 ]; then
  # Start the application using Docker Compose
  echo "Starting the application with Docker Compose..."
  docker-compose build --no-cache
  docker-compose up
else
  echo "Maven build failed. Aborting Docker Compose."
fi
