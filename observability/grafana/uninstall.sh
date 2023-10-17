#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

kubectl delete -f "${CURRENT_DIR}/cm-dashboard.yaml"

helm uninstall grafana