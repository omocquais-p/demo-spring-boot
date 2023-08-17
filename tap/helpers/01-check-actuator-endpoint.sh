#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

url=$(kubectl get route -o yaml | yq '.items[0].status.url')

info "URL: $url"

info "Checking health endpoint : $url/readyz"
STATUS=$(curl -X GET "$url"/readyz | jq -r .status)
if [ "$STATUS" == 'UP' ]
then
  success "Application is successfully started [status: $STATUS]"
else
  error "Application is NOT started"
fi