#!/bin/bash

#Get the PushGateway URL by running these commands in the same shell:
export POD_NAME=$(kubectl get pods --namespace apps -l "app=prometheus-pushgateway,component=pushgateway" -o jsonpath="{.items[0].metadata.name}")
kubectl --namespace apps port-forward $POD_NAME 9091