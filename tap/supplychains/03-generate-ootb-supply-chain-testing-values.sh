#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

TAP_REGISTRY_SERVER=$(tanzu package installed get tap --namespace tap-install --values | yq .ootb_supply_chain_basic.registry.server)
info "TAP_REGISTRY_SERVER=$TAP_REGISTRY_SERVER"

TAP_REPOSITORY=$(tanzu package installed get tap --namespace tap-install --values | yq .ootb_supply_chain_basic.registry.repository)
info "TAP_REPOSITORY=$TAP_REPOSITORY"

info "Generating the ootb-supply-chain-testing-values.yaml file"

ytt -f "$SCRIPT_DIR"/supplychains/ootb-supply-chain-testing-template.yaml --data-value registry.server="$TAP_REGISTRY_SERVER" --data-value registry.repository="$TAP_REPOSITORY" > "$SCRIPT_DIR"/supplychains/ootb-supply-chain-testing-values.yaml
cat "$SCRIPT_DIR"/supplychains/ootb-supply-chain-testing-values.yaml

success "ootb-supply-chain-testing-values.yaml successfully generated"