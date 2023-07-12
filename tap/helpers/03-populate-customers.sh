#!/usr/bin/env bash

# Get API URL
url=$(kubectl get route demo-spring-boot -o yaml | yq '.status.url')
echo "URL: $url"

if [ -z "$url" ]; then echo "URL unavailable"; exit 1; fi

status=$(curl -X GET $url/readyz | jq .status)
echo "Status: $status"

# Create a customer
echo "Create a customer"
uuid=$(curl -X POST "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'  | jq -r .uuid)
echo "uuid: $uuid"

# Get the customer - 1st call (check in the database and populate the cache)
echo "Get the customer - 1st call (check in the database and populate the cache)"
curl -X GET $url/customer/$uuid | jq .

# Get the customer - 2nd call (get data from the cache)
echo "Get the customer - 2nd call (get data from the cache)"
curl -X GET $url/customer/$uuid | jq .