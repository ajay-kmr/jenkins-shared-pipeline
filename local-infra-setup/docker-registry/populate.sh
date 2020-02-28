#!/bin/bash

docker tag joxit/docker-registry-ui:static localhost:5000/joxit/docker-registry-ui:static
docker tag joxit/docker-registry-ui:static localhost:5000/joxit/docker-registry-ui:0.3
docker tag joxit/docker-registry-ui:static localhost:5000/joxit/docker-registry-ui:0.3.0
docker tag joxit/docker-registry-ui:static localhost:5000/joxit/docker-registry-ui:0.3.0-static
docker tag joxit/docker-registry-ui:static localhost:5000/joxit/docker-registry-ui:0.3-static

docker tag registry:2.6.2 localhost:5000/registry:latest
docker tag registry:2.6.2 localhost:5000/registry:2.6.2
docker tag registry:2.6.2 localhost:5000/registry:2.6
docker tag registry:2.6.2 localhost:5000/registry:2.6.0
docker tag registry:2.6.2 localhost:5000/registry:2

docker push localhost:5000/joxit/docker-registry-ui:static
docker push localhost:5000/joxit/docker-registry-ui:0.3
docker push localhost:5000/joxit/docker-registry-ui:0.3.0
docker push localhost:5000/joxit/docker-registry-ui:0.3.0-static
docker push localhost:5000/joxit/docker-registry-ui:0.3-static
docker push localhost:5000/joxit/docker-registry-ui
docker push localhost:5000/registry:latest
docker push localhost:5000/registry:2.6.2
docker push localhost:5000/registry:2.6
docker push localhost:5000/registry:2.6.0
docker push localhost:5000/registry:2