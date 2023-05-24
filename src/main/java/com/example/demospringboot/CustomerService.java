package com.example.demospringboot;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public CustomerResponse create(CustomerDTO customerDTO) {
    Customer entity = new Customer();
    entity.setFirstName(customerDTO.firstName());
    entity.setLastName(customerDTO.lastName());
    Customer savedCustomer = customerRepository.save(entity);
    return new CustomerResponse(savedCustomer.getUuid(), savedCustomer.getFirstName(), savedCustomer.getLastName());
  }

  public CustomerResponse get(UUID uuid) {
    return customerRepository.findCust(uuid);
  }
}
