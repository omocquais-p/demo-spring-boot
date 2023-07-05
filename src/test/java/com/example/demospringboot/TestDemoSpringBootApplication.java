package com.example.demospringboot;

import com.example.demospringboot.configuration.MyContainersConfiguration;
import org.springframework.boot.SpringApplication;

public class TestDemoSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.from(DemoSpringBootApplication::main)
                      .with(MyContainersConfiguration.class)
                      .run(args);
  }

}
