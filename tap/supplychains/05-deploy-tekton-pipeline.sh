#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/../utils.sh"

info "Deploying Tekton pipeline"

kubectl apply -f "$SCRIPT_DIR"/tekton-pipeline.yaml

success "Tekton pipeline deployed"