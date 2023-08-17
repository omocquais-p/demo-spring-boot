#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && pwd)"
source "$SCRIPT_DIR/utils.sh"

SUPPLYCHAIN_SCRIPTS_DIR=$SCRIPT_DIR/supplychains

info "Install the Testing supplychain"

info "01-create-gitops-secret.sh"
"$SUPPLYCHAIN_SCRIPTS_DIR"/01-create-gitops-secret.sh

info "02-patch-service-account.sh"
"$SUPPLYCHAIN_SCRIPTS_DIR"/02-patch-service-account.sh

info "03-generate-ootb-supply-chain-testing-values.sh"
"$SUPPLYCHAIN_SCRIPTS_DIR"/03-generate-ootb-supply-chain-testing-values.sh

info "04-deploy-testing-supply-chain.sh"
"$SUPPLYCHAIN_SCRIPTS_DIR"/04-deploy-testing-supply-chain.sh

info "05-deploy-tekton-pipeline.sh"
"$SUPPLYCHAIN_SCRIPTS_DIR"/05-deploy-tekton-pipeline.sh

success "DONE"