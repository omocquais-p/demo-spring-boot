package com.example.demospringboot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class CustomerRepositoryTest {

  @Autowired
  CustomerRepository customerRepository;

  @DisplayName("it should persist a customer")
  @Test
  public void persist() {

    Customer customer = new Customer();
    customer.setFirstName("John");
    customer.setLastName("Smith");

    Customer saveCustomer = customerRepository.save(customer);
    assertThat(saveCustomer).isNotNull();
    assertThat(saveCustomer.getFirstName()).isNotNull().isEqualTo(customer.getFirstName());
    assertThat(saveCustomer.getLastName()).isNotNull().isEqualTo(customer.getLastName());
    assertThat(saveCustomer.getUuid()).isNotNull();

  }

  @DisplayName("it should return a customer")
  @Test
  public void find() {

    Customer customer = new Customer();
    customer.setFirstName("John");
    customer.setLastName("Smith111");

    Customer saveCustomer = customerRepository.save(customer);
    CustomerResponse customerResponse = customerRepository.findCust(saveCustomer.getUuid());

    assertThat(customerResponse).isNotNull();
    assertThat(customerResponse.firstName()).isNotNull().isEqualTo(customer.getFirstName());
    assertThat(customerResponse.name()).isNotNull().isEqualTo(customer.getLastName());
    assertThat(customerResponse.uuid()).isNotNull();

  }
}