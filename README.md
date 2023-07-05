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

## Build the native image with GraalVM

```
sdk use java 22.3.r19-grl
```

```
mvn -Pnative native:compile
```

```
mvn -Pnative spring-boot:build-image
```
