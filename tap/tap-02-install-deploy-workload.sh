#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/utils.sh"

info "create the claims"
"$SCRIPT_DIR"/01-claims.sh

info "Deploy the workload"
"$SCRIPT_DIR"/02-deploy.sh

success "Claims and Workload sucessfully deployed"