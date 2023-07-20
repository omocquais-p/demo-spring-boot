#!/usr/bin/env bash

echo "01-create-gitops-secret.sh"
./01-create-gitops-secret.sh

echo "02-patch-service-account.sh"
./02-patch-service-account.sh

echo "03-generate-ootb-supply-chain-testing-values.sh"
./03-generate-ootb-supply-chain-testing-values.sh

echo "04-deploy-testing-supply-chain.sh"
./04-deploy-testing-supply-chain.sh

echo "05-deploy-tekton-pipeline.sh"
./05-deploy-tekton-pipeline.sh

echo "DONE"