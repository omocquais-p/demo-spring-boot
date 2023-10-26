package com.example.demospringboot.controller;

import com.example.demospringboot.configuration.MyContainersConfiguration;
import com.example.demospringboot.dto.CustomerDTO;
import com.example.demospringboot.repository.Customer;
import com.example.demospringboot.repository.CustomerRepository;
import com.example.demospringboot.service.Company;
import com.example.demospringboot.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(MyContainersConfiguration.class)
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CustomerRepository customerRepository;

  @MockBean
  CompanyService companyService;

  private final ObjectMapper mapper = new ObjectMapper();
  private final ObjectWriter jsonWriter = mapper.writerFor(CustomerDTO.class);

  @BeforeEach
  public void reset() {
    customerRepository.deleteAll();
  }

  @DisplayName("Given a customer (firstname and a name), it should create a new customer")
  @Test
  public void create() throws Exception {
    long before = customerRepository.count();
    assertEquals(0, before);

    CustomerDTO customerDTO = new CustomerDTO("John", "Smith1");
    when(companyService.getCompany(customerDTO)).thenReturn(new Company("Kamino"));

    mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonWriter.writeValueAsString(customerDTO))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("John"))
            .andExpect(jsonPath("$.name").value("Smith1"))
            .andExpect(jsonPath("$.company").value("Kamino"));

    long after = customerRepository.count();
    assertEquals(1, after);

  }

  @DisplayName("Given a customer without firstname and name, it should throw a bad request exception")
  @Test
  public void validation() throws Exception {
    mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonWriter.writeValueAsString(new CustomerDTO(null, null)))
            )
            .andExpect(status().isBadRequest());
  }

  @DisplayName("Given an ID from an existing customer , it should return the customer")
  @Test
  public void getCustomer() throws Exception {

    Customer customer = new Customer();
    customer.setFirstName("Bob");
    customer.setLastName("Smith");
    customer.setCompanyName("abc");

    Customer customerSaved = customerRepository.save(customer);

    String uuid = customerSaved.getUuid().toString();

    mockMvc.perform(get("/customer/" + uuid)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
            .andExpect(jsonPath("$.name").value(customer.getLastName()))
            .andExpect(jsonPath("$.company").value(customer.getCompanyName()))
            .andExpect(jsonPath("$.uuid").isNotEmpty());
  }

}