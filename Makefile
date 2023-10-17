include .env
$(eval export $(shell sed -ne 's/ *#.*$$//; /./ s/=.*$$// p' .env))

SHELL=/bin/bash

kubeconfig:
	if [ -z ${KUBECONFIG} ]; then echo "KUBECONFIG is unset"; exit 1; else echo "KUBECONFIG is set to '$(KUBECONFIG)'"; fi

install: patch
	{ \
	set -e ;\
	./tap/tap-01-install-supply-chain.sh ;\
	./tap/tap-02-install-deploy-workload.sh ;\
	}

actuator: kubeconfig
	{ \
	set -e ;\
	./tap/helpers/01-check-actuator-endpoint.sh ;\
	}

customers: kubeconfig
	{ \
	set -e ;\
	./tap/helpers/03-populate-customers.sh ;\
	./tap/helpers/workload-logs.sh ;\
	}

patch: kubeconfig
	{ \
	set -e ;\
	./tap/tap-sandbox/patch-sandbox.sh ;\
	./tap/tap-sandbox/patch-sandbox-cpu.sh ;\
	./tap/tap-sandbox/patch-sandbox-memory.sh ;\
	}

deploy: kubeconfig
	{ \
	set -e ;\
	./tap/tap-02-install-deploy-workload.sh ;\
	}

build-image:
	{ \
	set -e ;\
	source /Users/omocquais/.sdkman/bin/sdkman-init.sh ;\
	sdk use java 17.0.8-tem  ;\
	./mvnw clean spring-boot:build-image ;\
	}

build-native:
	{ \
	set -e ;\
	source /Users/omocquais/.sdkman/bin/sdkman-init.sh ;\
	sdk use java 17.0.7-graal  ;\
	./mvnw clean -Pnative spring-boot:build-image ;\
	}

actuator-local:
	{ \
	set -e ;\
	./tap/helpers/01-check-actuator-endpoint.sh http://localhost:8080 ;\
	}

customers-local:
	{ \
	set -e ;\
	./tap/helpers/03-populate-customers.sh  http://localhost:8080 ;\
	}

start-app:
	{ \
	set -e ;\
	docker compose --profile observability up ;\
	}

start-backends:
	{ \
	set -e ;\
	docker compose up ;\
	}

start-app-maven:
	{ \
	set -e ;\
	./mvnw spring-boot:run ;\
	}

cleanup: kubeconfig
	{ \
	set -e ;\
	tanzu service class-claim delete postgres-1 --yes ;\
	tanzu service class-claim delete redis-1 --yes ;\
    tanzu apps workload delete demo-spring-boot --yes ;\
	}

grafana-forward: kubeconfig
	{ \
	set -e ;\
	./observability/grafana/port-forward.sh  ;\
	}

observability-install: kubeconfig
	{ \
	set -e ;\
	./observability/install.sh ;\
	}

observability-uninstall: kubeconfig
	{ \
	set -e ;\
	./observability/uninstall.sh ;\
	}

forward-app:
	{ \
	set -e ;\
	./observability/application/port-forward.sh ;\
	}
