package com.example.demospringboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RestClientTest(CompanyApiClient.class)
class CompanyApiClientTest {

    @Autowired
    CompanyApiClient companyApiClient;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Given an id, it should return the company name")
    @Test
    public void getCompanyName() throws JsonProcessingException {
        Integer id = 17;
        CompanyDTO dto = new CompanyDTO("Felucia");

        mockRestServiceServer.expect(requestTo("/" + id))
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(objectMapper.writeValueAsString(dto)));

        CompanyDTO company = companyApiClient.getCompanyName(id);

        assertNotNull(company);
        assertEquals("Felucia", company.name());
    }

}