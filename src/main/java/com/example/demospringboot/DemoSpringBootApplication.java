package com.example.demospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(MyRuntimeHints.class)
public class DemoSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoSpringBootApplication.class, args);
  }

}
