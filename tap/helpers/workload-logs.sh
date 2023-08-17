#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

info "tanzu apps workload tail demo-spring-boot --component run"
tanzu apps workload tail demo-spring-boot --component run "$@"
