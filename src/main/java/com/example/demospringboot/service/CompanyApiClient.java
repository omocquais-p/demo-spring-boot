package com.example.demospringboot.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CompanyApiClient {
    private final RestTemplate restTemplate;

    public CompanyApiClient(RestTemplateBuilder restTemplateBuilder, @Value("${api.company.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplateBuilder.rootUri(baseUrl).build();
    }

    public CompanyDTO getCompanyName(Integer id) {
        return restTemplate.getForObject("/"+id, CompanyDTO.class);
    }
}
