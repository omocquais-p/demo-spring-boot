# Spring Boot 3.1 with Testcontainers and Redis support

## Pre-requisites

### Redis

```
brew install redis
brew services info redis
brew install jq
```

## Start the application  with TestContainers support

```
SpringApplication.from(TestDemoSpringBootApplication::main).with(MyContainersConfiguration.class).run(args);
```

## Start the application with TestContainers support

### Start the application
```
./mvnw spring-boot:test-run
```

### Connect to Redis and delete all keys

```
redis-cli
FLUSHALL
```

### Check the actuator health endpoint

```
curl -X GET http://localhost:8080/actuator/health | jq .status
```

### Call the API to create a new customer
```
curl -X POST http://localhost:8080/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'  | jq .uuid
```

### Check Redis
```
KEYS *
```

```
127.0.0.1:6379> KEYS *
(empty array)
```

### Call the API to get the customer
```
curl -X GET http://localhost:8080/customer/0eae8211-e1b5-4a1b-8f04-1e6282ef290d
```

- In the application logs:
```
Try to get CustomerResponseDTO for uuid=0eae8211-e1b5-4a1b-8f04-1e6282ef290d

Try to get CustomerResponseDTO from database for uuid=0eae8211-e1b5-4a1b-8f04-1e6282ef290d
```

### Check the Redis cache

```
KEYS *
```

```
127.0.0.1:6379> KEYS *
1) "customer"
2) "customer:0eae8211-e1b5-4a1b-8f04-1e6282ef290d"
```

```
type customer:0eae8211-e1b5-4a1b-8f04-1e6282ef290d
hash
```

```
HGETALL "customer:0eae8211-e1b5-4a1b-8f04-1e6282ef290d"
```

```
127.0.0.1:6379> HGETALL "customer:0eae8211-e1b5-4a1b-8f04-1e6282ef290d"
1) "_class"
2) "com.example.demospringboot.repository.CustomerResponseDTO"
3) "firstName"
4) "John"
5) "name"
6) "Smith"
7) "uuid"
8) "0eae8211-e1b5-4a1b-8f04-1e6282ef290d"
```

### Call again the API to get the customer

```
curl -X GET http://localhost:8080/customer/0eae8211-e1b5-4a1b-8f04-1e6282ef290d
```

- Expected Response
```
{"uuid":"0eae8211-e1b5-4a1b-8f04-1e6282ef290d","firstName":"John","name":"Smith"}
```

```
Try to get CustomerResponseDTO for uuid=0eae8211-e1b5-4a1b-8f04-1e6282ef290d
CustomerResponseDTO found on Cache for uuid=0eae8211-e1b5-4a1b-8f04-1e6282ef290d
```

## Start the application

- Docker-compose support (compose.yaml) is used to start Redis and PostgreSQL

```
./mvnw spring-boot:run -P docker-compose
```

- Testcontainers support is used to start Redis and PostgreSQL

```
./mvnw spring-boot:test-run
```

- Get the Redis port (started by TestContainer)

```
docker ps | grep redis 
48790f9f72fe   redis:5.0.3-alpine          "docker-entrypoint.s…"   3 minutes ago   Up 3 minutes   0.0.0.0:52943->6379/tcp   fervent_shaw
```

```
redis-cli -p 52943

127.0.0.1:52943> KEYS *
1) "customer"
2) "customer:f4f362ad-ac91-4fcc-8154-bc07830868ac"
```

### Deploy to Tanzu Application Platform (TAP) 1.5

- Redis - claim

```
  tanzu service class-claim create redis-1 --class redis-unmanaged
```

- PostgreSQL - claim

```
tanzu service class-claim create postgres-1 --class postgresql-unmanaged
```

- Claims check

```
tanzu service class-claim list
NAME        CLASS                 READY  REASON  
postgres-1  postgresql-unmanaged  True   Ready   
redis-1     redis-unmanaged       True   Ready
``` 

- Deploy the application with Tanzu CLI

```
tanzu apps workload apply demo-spring-boot --git-repo https://github.com/omocquais-p/demo-spring-boot.git --git-branch master --type web --build-env BP_JVM_VERSION=17 --service-ref="db1=services.apps.tanzu.vmware.com/v1alpha1:ClassClaim:postgres-1" --service-ref="red1=services.apps.tanzu.vmware.com/v1alpha1:ClassClaim:redis-1"
```

- Check the logs

```
tanzu apps workload tail demo-spring-boot --timestamp --since 1h
```

- Check if the app is READY

```
tanzu apps workload get demo-spring-boot
```

- Check the Health endpoint from Spring Boot Actuator

```
curl -X GET https://{url}/actuator/health | jq .
```

```
{
"status": "UP",
"groups": [
"liveness",
"readiness"
]
}
```

- Create a customer with an API call 

```
curl -X POST https://{url}/customer -H 'Content-Type: application/json' -d '{"firstName":  "John", "lastName": "Smith"}'  | jq .uuid
```

- Fisrt call: found from database

```
curl -X GET https://{url}/customer/4fb6df33-61b0-4f43-a4b6-8a5b19805e0d  | jq .
```

```
{
"uuid": "4fb6df33-61b0-4f43-a4b6-8a5b19805e0d",
"firstName": "John",
"name": "Smith"
}
```


```
2023-07-05T12:38:41.678Z  INFO 1 --- [nio-8080-exec-9] c.e.d.service.CustomerService            : Try to get CustomerResponseDTO for uuid=4fb6df33-61b0-4f43-a4b6-8a5b19805e0d
2023-07-05T12:38:41.684Z  INFO 1 --- [nio-8080-exec-9] c.e.d.service.CustomerService            : Try to get CustomerResponseDTO from database for uuid=4fb6df33-61b0-4f43-a4b6-8a5b19805e0d
```

- Second call: found on cache

```
curl -X GET https://{url}/customer/4fb6df33-61b0-4f43-a4b6-8a5b19805e0d | jq .
```

```
2023-07-05T12:40:13.127Z  INFO 1 --- [nio-8080-exec-7] c.e.d.service.CustomerService            : Try to get CustomerResponseDTO for uuid=4fb6df33-61b0-4f43-a4b6-8a5b19805e0d
2023-07-05T12:40:14.149Z  INFO 1 --- [nio-8080-exec-7] c.e.d.service.CustomerService            : CustomerResponseDTO found on Cache for uuid=4fb6df33-61b0-4f43-a4b6-8a5b19805e0d
```

## Build the native image with GraalVM

### Set the JDK
```
sdk install java 17.0.7-graal
sdk use java 17.0.7-graal
```

### Build the native image
```
mvn clean -Pnative spring-boot:build-image
```

Successfully built image 'docker.io/library/demo-spring-boot:0.0.1-SNAPSHOT'

### Run the application (with Redis and PostgreSQL)
```
cd native
docker compose up
```

- TODO: Fix this exception
```
java.lang.NoSuchMethodException: org.hibernate.id.uuid.UuidGenerator.<init>
```