package com.example.demospringboot;

import org.springframework.boot.SpringApplication;

public class TestDemoSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.from(TestDemoSpringBootApplication::main).with(MyContainersConfiguration.class).run(args);
  }

}
