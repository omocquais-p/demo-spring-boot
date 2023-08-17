#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

info "Checking the logs"

tanzu apps workload tail demo-spring-boot --timestamp --since 1h

success "Logs checked"