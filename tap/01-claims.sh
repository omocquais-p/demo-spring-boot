#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/utils.sh"

tanzu services classes list

info "REDIS - tanzu service class-claim create redis-1 --class redis-unmanaged"
tanzu service class-claim create redis-1 --class redis-unmanaged

kubectl wait --for=condition=ready ClassClaim/redis-1 --timeout=5m

info "POSTGRESQL - tanzu service class-claim create postgres-1 --class postgresql-unmanaged"
tanzu service class-claim create postgres-1 --class postgresql-unmanaged

kubectl wait --for=condition=ready ClassClaim/postgres-1 --timeout=5m

info "CHECKS - tanzu service class-claim list"
tanzu service class-claim list

success "DONE"