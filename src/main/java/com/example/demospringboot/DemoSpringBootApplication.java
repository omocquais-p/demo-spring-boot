package com.example.demospringboot;

import com.example.demospringboot.hints.MyRuntimeHints;
import com.example.demospringboot.service.CompanyDTO;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(MyRuntimeHints.class)
@RegisterReflectionForBinding({CompanyDTO.class})
public class DemoSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoSpringBootApplication.class, args);
  }

}
