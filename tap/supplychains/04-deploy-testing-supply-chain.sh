#!/usr/bin/env bash

tanzu package install ootb-supply-chain-testing --package ootb-supply-chain-testing.tanzu.vmware.com  --version 0.12.6 --namespace tap-install --values-file ootb-supply-chain-testing-values.yaml

#tanzu package installed update ootb-supply-chain-testing -p ootb-supply-chain-testing.tanzu.vmware.com --version 0.12.6 --namespace tap-install --values-file ootb-supply-chain-testing-values.yaml
