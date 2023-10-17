#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

# Prometheus
echo "Installing Prometheus"
"${CURRENT_DIR}"/prometheus/install.sh

echo "PROMETHEUS - WAITING"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=prometheus  --timeout=5m
echo "PROMETHEUS - OK"

# Loki
echo "Installing Loki"
"${CURRENT_DIR}"/logging/install.sh

echo "LOKI - WAITING"
kubectl wait --for=condition=ready pod -l app=loki --timeout=5m
echo "LOKI - OK"

# Tempo
echo "Installing Tempo"
"${CURRENT_DIR}"/tempo/install.sh

echo "TEMPO - WAITING"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=tempo  --timeout=5m
echo "TEMPO - OK"

# Grafana
echo "Installing Grafana"
"${CURRENT_DIR}"/grafana/install.sh

sleep 10

echo "GRAFANA - WAITING"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=grafana  --timeout=5m
echo "GRAFANA - OK"

sleep 10

echo "DONE"