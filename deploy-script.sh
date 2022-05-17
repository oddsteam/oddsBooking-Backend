#!/usr/bin/env bash
docker login -u ap-southeast-2@OA4R6SQSJDS6O5TPXWUJ -p 092929273c8458b0141bdca0a6475a3f3103eb3f4fa57b4a5405635828bc4c9a ${REGISTRY}
docker compose pull
docker compose down
docker compose up -d