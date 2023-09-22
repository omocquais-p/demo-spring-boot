#!/bin/bash

POD_SECURITY=$(kubectl get ns apps -o yaml | yq '.metadata.labels["pod-security.kubernetes.io/enforce"]' | tr -d '\n')
if [ "$POD_SECURITY" == "baseline" ]; then
    echo "The Pod security policy should not be set on the apps namespace, but it was"
    echo "Patching the cluster policy"
    kubectl get clusterpolicy sandbox-security-policies -o yaml \
        | yq '.spec.rules[0].exclude = { "any": [{ "resources": { "namespaces": ["apps"]  } }]}' \
        | kubectl apply -f -
    echo "Removing the pod security label from the apps namespace"
    kubectl label namespace apps "pod-security.kubernetes.io/enforce"-
else
    echo "Pod security policy is configured correctly"
fi