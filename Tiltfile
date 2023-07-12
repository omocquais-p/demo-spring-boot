SOURCE_IMAGE = os.getenv("SOURCE_IMAGE", default='us-central1-docker.pkg.dev/tap-sandbox-dev/tap-polite-quetzal/demo-spring-boot-developer-ns-source')
LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='developer-ns')

k8s_custom_deploy(
   'demo-spring-boot',
   apply_cmd="tanzu apps workload apply -f catalog/catalog-info.yaml --live-update" +
       " --local-path " + LOCAL_PATH +
       " --source-image " + SOURCE_IMAGE +
       " --namespace " + NAMESPACE +
       " --yes >/dev/null" +
       " && kubectl get workload demo-spring-boot --namespace " + NAMESPACE + " -o yaml",
   delete_cmd="tanzu apps workload delete -f catalog/catalog-info.yaml --namespace " + NAMESPACE + " --yes" ,
   deps=['pom.xml', './target/classes'],
   container_selector='workload',
   live_update=[
       sync('./target/classes', '/workspace/BOOT-INF/classes')
   ]
)

k8s_resource('demo-spring-boot', port_forwards=["8080:8080"],
   extra_pod_selectors=[{'carto.run/workload-name': 'demo-spring-boot', 'app.kubernetes.io/component': 'run'}])
allow_k8s_contexts('CONTEXT-NAME')