#!/usr/bin/env bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" &> /dev/null && cd .. && pwd)"
source "$SCRIPT_DIR/utils.sh"

if [ -z "$1" ]
then
  # Get API URL
  url=$(kubectl get route demo-spring-boot -o yaml | yq '.status.url')
else
  url=$1
fi

info "URL: $url"

if [ -z "$url" ]; then echo "URL unavailable"; exit 1; fi

status=$(curl -X GET "$url"/readyz | jq .status)
info "Status: $status"

# Create a customer
info "Create a customer"
uuid=$(curl -X POST "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'  | jq -r .uuid)
success "uuid: $uuid"

# Get the customer - 1st call (check in the database and populate the cache)
info "Get the customer - 1st call (check in the database and populate the cache)"
curl -X GET "$url"/customer/"$uuid" | jq .

# Get the customer - 2nd call (get data from the cache)
info "Get the customer - 2nd call (get data from the cache)"
curl -X GET "$url"/customer/"$uuid" | jq .