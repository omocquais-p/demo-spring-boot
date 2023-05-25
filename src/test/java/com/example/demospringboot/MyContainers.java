package com.example.demospringboot;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public interface MyContainers {

  @Container
  @ServiceConnection
  PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.3-alpine");

  @Container
  @ServiceConnection
  GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);
}
