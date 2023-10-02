#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/../utils.sh"

PACKAGE_VERSION=0.13.9
#tanzu package available get ootb-supply-chain-testing.tanzu.vmware.com -n tap-install
info "Install ootb-supply-chain-testing.tanzu.vmware.com package version: $PACKAGE_VERSION"

tanzu package install ootb-supply-chain-testing --package ootb-supply-chain-testing.tanzu.vmware.com  --version $PACKAGE_VERSION --namespace tap-install --values-file "$SCRIPT_DIR"/ootb-supply-chain-testing-values.yaml

kubectl get csc source-test-to-url

success "ootb-supply-chain-testing package sucessfully installed"