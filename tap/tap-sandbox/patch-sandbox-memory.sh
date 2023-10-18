echo "Patching the cluster policy - limits.memory"

kubectl get clusterpolicy sandbox-namespace-limits -o yaml \
  | yq eval '(.spec.rules[] | select(.name == "deploy-resource-quotas")).generate.data.spec.hard."limits.memory"="22Gi"' - \
  | kubectl apply -f -
