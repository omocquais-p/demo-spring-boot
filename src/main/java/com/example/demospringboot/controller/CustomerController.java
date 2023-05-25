package com.example.demospringboot.controller;

import com.example.demospringboot.dto.CustomerDTO;
import com.example.demospringboot.repository.CustomerResponseDTO;
import com.example.demospringboot.service.CustomerService;
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
  public CustomerResponseDTO create(@RequestBody CustomerDTO customerDTO) {
    return customerService.create(customerDTO);
  }

  @GetMapping("/customer/{uuid}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public CustomerResponseDTO get(@PathVariable String uuid) {
    return customerService.get(UUID.fromString(uuid));
  }
}
