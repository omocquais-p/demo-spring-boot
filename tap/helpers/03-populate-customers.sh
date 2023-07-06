#!/usr/bin/env bash

# Get API URL
url=$(kubectl get route -o yaml | yq '.items[0].status.url')
echo "URL: $url"

# Create a customer
uuid=$(curl -X POST "$url"/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'  | jq -r .uuid)
echo "uuid: $uuid"

# Get the customer - 1st call (check in the database and populate the cache)
curl -X GET $url/customer/$uuid | jq .

# Get the customer - 2nd call (get data from the cache)
curl -X GET $url/customer/$uuid | jq .