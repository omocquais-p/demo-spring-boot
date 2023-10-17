#!/bin/bash

set -e

echo "In order to authenticate on the grafana frontend please use the following credentials:"
echo "username: admin"
echo -n "password: "
kubectl get secret grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo

echo "url: http://localhost:3000"
kubectl port-forward svc/grafana 3000:80