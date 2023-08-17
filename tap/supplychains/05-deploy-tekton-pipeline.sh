#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

info "Deploying Tekton pipeline"

kubectl apply -f "$SCRIPT_DIR"/supplychains/tekton-pipeline.yaml

success "Tekton pipeline deployed"