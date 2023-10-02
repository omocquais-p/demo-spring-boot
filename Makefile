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
	./tap/tap-sandbox/patch-sandbox.sh;\
	}

deploy: kubeconfig
	{ \
	set -e ;\
	./tap/tap-02-install-deploy-workload.sh ;\
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

start-native:
	{ \
	set -e ;\
	docker compose --profile app up ;\
	}