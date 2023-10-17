#!/bin/bash

set -e

helm repo add grafana https://grafana.github.io/helm-charts

helm repo update

helm install loki grafana/loki-stack