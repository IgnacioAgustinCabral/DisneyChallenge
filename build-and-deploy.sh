#!/bin/bash

docker image prune -f

# Start the application using Docker Compose
echo "Starting the application with Docker Compose..."
docker-compose up --build
