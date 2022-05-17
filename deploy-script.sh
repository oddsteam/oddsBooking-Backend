#!/usr/bin/env bash
docker login -u ap-southeast-2@H97WABNOA1NBRPW8INUL -p aa275bca967ab0e83dccf3c57efb23ff981d9cd8ae4c66089d4aa25cdf971292 ${REGISTRY}
docker compose pull
docker compose down
docker compose up -d