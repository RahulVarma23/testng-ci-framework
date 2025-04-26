#!/bin/bash

# Build and run tests in Docker
docker-compose up --build

# Copy test results from container
mkdir -p target
docker cp $(docker ps -lq):/app/target/surefire-reports ./target/
docker cp $(docker ps -lq):/app/target/extent-reports ./target/
docker cp $(docker ps -lq):/app/target/screenshots ./target/

# Clean up
docker-compose down