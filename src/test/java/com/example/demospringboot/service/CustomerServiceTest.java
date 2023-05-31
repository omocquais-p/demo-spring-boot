package com.example.demospringboot.service;

import com.example.demospringboot.dto.CustomerDTO;
import com.example.demospringboot.repository.Customer;
import com.example.demospringboot.repository.CustomerCacheRepository;
import com.example.demospringboot.repository.CustomerRepository;
import com.example.demospringboot.repository.CustomerResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
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

  @Mock
  CustomerCacheRepository customerCacheRepository;

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

    CustomerResponseDTO customerResponseDTO = customerService.create(customerDTO);

    verify(customerRepository).save(any());
    assertNotNull(customerResponseDTO);
    assertEquals(customerDTO.firstName(), customerResponseDTO.firstName());
    assertEquals(customerDTO.lastName(), customerResponseDTO.name());
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
    when(customerRepository.findCust(uuid)).thenReturn(new CustomerResponseDTO(uuid, firstName, lastName));

    CustomerResponseDTO customerResponseDTO = customerService.get(uuid);
    assertNotNull(customerResponseDTO);
    assertEquals(firstName, customerResponseDTO.firstName());
    assertEquals(lastName, customerResponseDTO.name());
  }

  @DisplayName("given an existing customer with a specific UUID, it should return a customer response that holds customer details and save information into cache")
  @Test
  public void findAndSaveIntoCache() {
    UUID uuid = UUID.randomUUID();
    String lastName = "Smith";
    String firstName = "John";

    Customer entity = new Customer();
    entity.setLastName(lastName);
    entity.setFirstName(firstName);
    entity.setUuid(uuid);

    when(customerCacheRepository.findById(uuid)).thenReturn(Optional.empty());
    when(customerRepository.findCust(uuid)).thenReturn(new CustomerResponseDTO(uuid, firstName, lastName));

    var customerResponseDTO = customerService.get(uuid);
    verify(customerCacheRepository).save(any(CustomerResponseDTO.class));
    assertNotNull(customerResponseDTO);
    assertEquals(firstName, customerResponseDTO.firstName());
    assertEquals(lastName, customerResponseDTO.name());
  }

  @DisplayName("given an existing customer with a specific UUID saved into cache, it should return a customer response from cache")
  @Test
  public void getFromSave() {
    UUID uuid = UUID.randomUUID();
    String lastName = "Smith";
    String firstName = "John";

    Customer entity = new Customer();
    entity.setLastName(lastName);
    entity.setFirstName(firstName);
    entity.setUuid(uuid);

    CustomerResponseDTO responseDTO = new CustomerResponseDTO(uuid, firstName, lastName);
    when(customerCacheRepository.findById(uuid)).thenReturn(Optional.of(responseDTO));

    var customerResponseDTO = customerService.get(uuid);
    verify(customerCacheRepository).findById(uuid);

    assertNotNull(customerResponseDTO);
    assertEquals(firstName, customerResponseDTO.firstName());
    assertEquals(lastName, customerResponseDTO.name());
  }
}