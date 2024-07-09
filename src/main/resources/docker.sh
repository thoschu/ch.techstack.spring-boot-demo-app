#!/usr/bin/env bash

docker compose ps -a
docker compose down
docker compose up -d
sleep 3
docker compose ps -a
