echo "Patching the cluster policy - limits.cpu"
kubectl get clusterpolicy sandbox-namespace-limits -o yaml \
  | yq eval '(.spec.rules[] | select(.name == "deploy-resource-quotas")).generate.data.spec.hard."limits.cpu"= 32' - \
  | kubectl apply -f -

echo "kubectl describe quota sandbox-resource-quotas"
kubectl describe quota sandbox-resource-quotas