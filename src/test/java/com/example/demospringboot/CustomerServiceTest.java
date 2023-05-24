package com.example.demospringboot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  CustomerRepository customerRepository;

  @InjectMocks
  CustomerService customerService;

  @DisplayName("given a customer, it should return a customer response")
  @Test
  public void create() {
    CustomerDTO customerDTO = new CustomerDTO("John", "Smith");

    Customer entity = new Customer();
    entity.setLastName(customerDTO.lastName());
    entity.setFirstName(customerDTO.firstName());
    entity.setUuid(UUID.randomUUID());

    when(customerRepository.save(any(Customer.class))).thenReturn(entity);

    CustomerResponse customerResponse = customerService.create(customerDTO);

    verify(customerRepository).save(any());
    assertNotNull(customerResponse);
    assertEquals(customerDTO.firstName(), customerResponse.firstName());
    assertEquals(customerDTO.lastName(), customerResponse.name());
  }

  @DisplayName("given an existing customer with a specific UUID, it should return a customer response that holds customer details")
  @Test
  public void get() {

    UUID uuid = UUID.randomUUID();
    String lastName = "Smith";
    String firstName = "John";

    Customer entity = new Customer();
    entity.setLastName(lastName);
    entity.setFirstName(firstName);
    entity.setUuid(uuid);
    when(customerRepository.findCust(uuid)).thenReturn(new CustomerResponse(uuid, firstName, lastName));

    CustomerResponse customerResponse = customerService.get(uuid);
    assertNotNull(customerResponse);
    assertEquals(firstName, customerResponse.firstName());
    assertEquals(lastName, customerResponse.name());
  }
}