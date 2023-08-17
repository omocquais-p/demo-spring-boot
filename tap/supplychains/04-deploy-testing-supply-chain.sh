#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

SUPPLYCHAIN_SCRIPTS_DIR=$SCRIPT_DIR/supplychains

PACKAGE_VERSION=0.13.6
info "Install ootb-supply-chain-testing.tanzu.vmware.com package version: $PACKAGE_VERSION"

tanzu package install ootb-supply-chain-testing --package ootb-supply-chain-testing.tanzu.vmware.com  --version $PACKAGE_VERSION --namespace tap-install --values-file "$SUPPLYCHAIN_SCRIPTS_DIR"/ootb-supply-chain-testing-values.yaml

kubectl get csc source-test-to-url

success "ootb-supply-chain-testing package sucessfully installed"

#tanzu package installed update ootb-supply-chain-testing -p ootb-supply-chain-testing.tanzu.vmware.com --version 0.13.6 --namespace tap-install --values-file ootb-supply-chain-testing-values.yaml