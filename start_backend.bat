@echo off
docker compose -f docker.compose.yml up --build --scale rest-api=3
