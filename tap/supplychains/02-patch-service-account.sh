#!/usr/bin/env bash
kubectl get serviceaccount default -o yaml
kubectl patch serviceaccount default -p '{"secrets": [{"name": "git-github-ssh"}, {"name": "registries-credentials"}]}'
kubectl get serviceaccount default -o yaml