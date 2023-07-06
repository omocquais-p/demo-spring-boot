#!/usr/bin/env bash

echo "Deploy workload"
tanzu apps workload apply demo-spring-boot --git-repo https://github.com/omocquais-p/demo-spring-boot.git --git-branch master --type web --build-env BP_JVM_VERSION=17 --service-ref="db1=services.apps.tanzu.vmware.com/v1alpha1:ClassClaim:postgres-1" --service-ref="red1=services.apps.tanzu.vmware.com/v1alpha1:ClassClaim:redis-1"

echo "DONE"