package com.example.demospringboot.service;

import com.example.demospringboot.dto.CustomerDTO;
import com.example.demospringboot.repository.CustomerResponseDTO;
import com.example.demospringboot.repository.Customer;
import com.example.demospringboot.repository.CustomerCacheRepository;
import com.example.demospringboot.repository.CustomerRepository;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

  private final CustomerRepository customerRepository;

  private final CustomerCacheRepository customerCacheRepository;

  public CustomerService(CustomerRepository customerRepository, CustomerCacheRepository customerCacheRepository) {
    this.customerRepository = customerRepository;
    this.customerCacheRepository = customerCacheRepository;
  }

  public CustomerResponseDTO create(CustomerDTO customerDTO) {
    Customer entity = new Customer();
    entity.setFirstName(customerDTO.firstName());
    entity.setLastName(customerDTO.lastName());
    Customer savedCustomer = customerRepository.save(entity);
    return new CustomerResponseDTO(savedCustomer.getUuid(), savedCustomer.getFirstName(), savedCustomer.getLastName());
  }

  @Observed(name = "get",
          contextualName = "getting-customer",
          lowCardinalityKeyValues = {"userType", "userType2"})
  public CustomerResponseDTO get(UUID uuid) {
    LOGGER.info("Try to get CustomerResponseDTO for uuid={}", uuid);
    Optional<CustomerResponseDTO> optCustomerResponseDTO = customerCacheRepository.findById(uuid);
    if (optCustomerResponseDTO.isPresent()) {
      LOGGER.info("CustomerResponseDTO found on Cache for uuid={}", uuid);
      return optCustomerResponseDTO.get();
    } else {
      LOGGER.info("Try to get CustomerResponseDTO from database for uuid={}", uuid);
      var customerResponseDTO = customerRepository.findCust(uuid);
      if (customerResponseDTO != null) {
        customerCacheRepository.save(customerResponseDTO);
      }
      return customerResponseDTO;
    }
  }
}
