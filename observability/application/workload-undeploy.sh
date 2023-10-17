#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

echo "Deploying the workload"
tanzu apps workload delete -f "${CURRENT_DIR}/../../config/workload.yaml" --yes