package com.example.demospringboot;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public interface MyContainers {

  @Container
  @ServiceConnection
  PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:15.3-alpine");
}
