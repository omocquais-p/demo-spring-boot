#!/usr/bin/env bash

tap_registry_server=$(tanzu package installed get tap --namespace tap-install --values | yq .ootb_supply_chain_basic.registry.server)
export tap_registry_server
echo "$tap_registry_server"

tap_repository=$(tanzu package installed get tap --namespace tap-install --values | yq .ootb_supply_chain_basic.registry.repository)
export tap_repository
echo "$tap_repository"

ytt -f ootb-supply-chain-testing-template.yaml --data-value registry.server="$tap_registry_server" --data-value registry.repository="$tap_repository" | kubectl apply -f-