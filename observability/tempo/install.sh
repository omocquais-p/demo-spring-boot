#!/bin/bash

set -e

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

helm repo add grafana https://grafana.github.io/helm-charts

helm repo update

helm install tempo grafana/tempo -f "${CURRENT_DIR}/values.yaml"