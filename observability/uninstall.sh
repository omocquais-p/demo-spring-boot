#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

# Prometheus
"${CURRENT_DIR}"/prometheus/uninstall.sh

# Loki
"${CURRENT_DIR}"/logging/uninstall.sh

# Tempo
"${CURRENT_DIR}"/tempo/uninstall.sh

# Grafana
"${CURRENT_DIR}"/grafana/uninstall.sh