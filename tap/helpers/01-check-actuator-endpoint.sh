#!/usr/bin/env bash

url=$(kubectl get route -o yaml | yq '.items[0].status.url')
echo "URL: $url"
curl -X GET "$url"/actuator/health | jq .