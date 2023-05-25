package com.example.demospringboot.repository;

import com.example.demospringboot.CustomerResponseDTO;
import com.example.demospringboot.MyContainersConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataRedisTest
@Import(MyContainersConfiguration.class)
class CustomerCacheRepositoryTest {

  @Autowired
  private CustomerCacheRepository customerCacheRepository;

  @DisplayName("Given a customer saved into cache, It should retrieve it from uuid")
  @Test
  public void shouldRetrieveFromCache() {
    var uuid = UUID.randomUUID();
    var customerResponseDTO = new CustomerResponseDTO(uuid, "John", "Smith");
    var cachedDTO = customerCacheRepository.save(customerResponseDTO);
    assertNotNull(cachedDTO);

    Optional<CustomerResponseDTO> optCustomerResponseDTO = customerCacheRepository.findById(uuid);
    assertNotNull(optCustomerResponseDTO);
    assertTrue(optCustomerResponseDTO.isPresent());
    assertEquals(customerResponseDTO.uuid(), optCustomerResponseDTO.get().uuid());
    assertEquals(customerResponseDTO.firstName(), optCustomerResponseDTO.get().firstName());
    assertEquals(customerResponseDTO.name(), optCustomerResponseDTO.get().name());

  }

}