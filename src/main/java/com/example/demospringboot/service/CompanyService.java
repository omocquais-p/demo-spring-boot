package com.example.demospringboot.service;

import com.example.demospringboot.dto.CustomerDTO;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CompanyService {

    private final CompanyApiClient companyApiClient;
    private final boolean useApi;

    public CompanyService(CompanyApiClient companyApiClient, @Value("${api.company.enabled:false}") boolean useApi) {
        this.companyApiClient = companyApiClient;
        this.useApi = useApi;
    }

    @Observed(name = "getCompany",
            contextualName = "getting-company",
            lowCardinalityKeyValues = {"userType", "userType3"})
    public Company getCompany(CustomerDTO customer) {
        Integer id = (customer.firstName() + customer.lastName()).length();

        String name = useApi ? companyApiClient.getCompanyName(id).name() : "Felucia-"+ Instant.now().toEpochMilli();

        return new Company(name);
    }
}
