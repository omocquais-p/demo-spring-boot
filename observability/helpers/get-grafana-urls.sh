#!/bin/bash

export POD_NAME=$(kubectl get pods --namespace apps -l "app.kubernetes.io/name=grafana,app.kubernetes.io/instance=grafana" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace apps port-forward $POD_NAME 3000
