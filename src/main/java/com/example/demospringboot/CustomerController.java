package com.example.demospringboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping("/customer")
  @ResponseStatus(HttpStatus.CREATED)
  public CustomerResponse create(@RequestBody CustomerDTO customerDTO) {
    return customerService.create(customerDTO);
  }

  @GetMapping("/customer/{uuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public CustomerResponse get(@PathVariable String uuid) {
    return customerService.get(UUID.fromString(uuid));
  }
}
