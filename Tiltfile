LOCAL_PATH = os.getenv("LOCAL_PATH", default='.')
NAMESPACE = os.getenv("NAMESPACE", default='apps')

update_settings (k8s_upsert_timeout_secs = 300)

k8s_custom_deploy(
    'demo-spring-boot',
    apply_cmd="tanzu apps workload apply -f config/workload.yaml --update-strategy replace --debug --live-update" +
              " --local-path " + LOCAL_PATH +
              " --namespace " + NAMESPACE +
              " --yes --output yaml",
    delete_cmd="tanzu apps workload delete -f config/workload.yaml --namespace " + NAMESPACE + " --yes",
    deps=['pom.xml', './target/classes'],
    container_selector='workload',
    live_update=[
      sync('./target/classes', '/workspace/BOOT-INF/classes')
    ]
)

k8s_resource('demo-spring-boot', port_forwards=["8080:8080"],
            extra_pod_selectors=[{'carto.run/workload-name': 'demo-spring-boot','app.kubernetes.io/component': 'run'}])

allow_k8s_contexts('tap-sandbox')