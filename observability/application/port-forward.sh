#!/bin/bash

echo "url: http://localhost:8080"
kubectl port-forward svc/demo-spring-boot-00001-private 8080:80