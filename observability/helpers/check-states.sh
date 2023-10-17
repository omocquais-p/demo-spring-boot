#!/bin/bash

echo "LOKI - WAITING"
kubectl wait --for=condition=ready pod -l app=loki --timeout=5m
echo "LOKI - OK"

echo "TEMPO - WAITING"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=tempo  --timeout=5m
echo "TEMPO - OK"

echo "PROMETHEUS - WAITING"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=prometheus  --timeout=5m
echo "PROMETHEUS - OK"

