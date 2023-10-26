package com.example.demospringboot.service;

import com.example.demospringboot.dto.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RestClientTest
class CompanyServiceTest {

    @Mock
    CompanyApiClient companyApiClient;

    @DisplayName("Given an uuid starting, it should return a company name")
    @Test
    void getCompany() throws JsonProcessingException {

        String json = """
                {
                    "name": "Felucia"
                }
                """;
        CustomerDTO customer = new CustomerDTO("John", "Smith");
        Integer id = (customer.firstName() + customer.lastName()).length();
        System.out.println("id = " + id);
        when(companyApiClient.getCompanyName(id)).thenReturn(new ObjectMapper().readValue(json, CompanyDTO.class));

        Company company = new CompanyService(companyApiClient).getCompany(customer);

        verify(companyApiClient).getCompanyName(id);
        assertEquals("Felucia", company.name());
    }

}