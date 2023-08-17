SHELL=/bin/bash

install:
	{ \
	set -e ;\
	export KUBECONFIG=$(HOME)/Documents/dev/tap-sandbox/2023-08-17/config ;\
	./tap/tap-01-install-supply-chain.sh ;\
	./tap/tap-02-install-deploy-workload.sh ;\
	}

actuator:
	{ \
	set -e ;\
	export KUBECONFIG=$(HOME)/Documents/dev/tap-sandbox/2023-08-17/config ;\
	./tap/helpers/01-check-actuator-endpoint.sh ;\
	}

customers:
	{ \
	set -e ;\
	export KUBECONFIG=$(HOME)/Documents/dev/tap-sandbox/2023-08-17/config ;\
	./tap/helpers/03-populate-customers.sh ;\
	./tap/helpers/workload-logs.sh ;\
	}