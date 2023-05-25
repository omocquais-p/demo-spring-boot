package com.example.demospringboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(MyContainersConfiguration.class)
class CustomerControllerTest
//        implements MyContainers
{

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CustomerRepository customerRepository;

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
    mockMvc.perform(post("/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonWriter.writeValueAsString(new CustomerDTO("John", "Smith1")))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName").value("John"));

    long after = customerRepository.count();
    assertEquals(1, after);

  }

  @DisplayName("Given an ID from an existing customer , it should return the customer")
  @Test
  public void getCustomer() throws Exception {

    Customer customer = new Customer();
    customer.setFirstName("Bob");
    customer.setLastName("Smith");
    Customer customerSaved = customerRepository.save(customer);
    String uuid = customerSaved.getUuid().toString();

    mockMvc.perform(get("/customer/" + uuid)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.firstName").value("Bob"))
            .andExpect(jsonPath("$.name").value("Smith"))
            .andExpect(jsonPath("$.uuid").isNotEmpty());
  }

}