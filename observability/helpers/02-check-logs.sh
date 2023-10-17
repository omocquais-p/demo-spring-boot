#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils/utils.sh"

APP_NAME=demo-spring-boot

info "Checking the logs"

tanzu apps workload tail $APP_NAME --timestamp --since 1h

success "Logs checked"