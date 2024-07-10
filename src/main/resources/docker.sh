#!/usr/bin/env bash

docker ps -a

docker compose down

docker rmi thoschu/spring-boot-demo-app

docker compose up -d

sleep 3

docker ps -a

echo ">>>>>>>>>> http://localhost:8080/ <<<<<<<<<<"
