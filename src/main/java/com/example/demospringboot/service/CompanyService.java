package com.example.demospringboot.service;

import com.example.demospringboot.dto.CustomerDTO;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    private final CompanyApiClient companyApiClient;

    public CompanyService(CompanyApiClient companyApiClient) {
        this.companyApiClient = companyApiClient;
    }

    @Observed(name = "getCompany",
            contextualName = "getting-company",
            lowCardinalityKeyValues = {"userType", "userType3"})
    public Company getCompany(CustomerDTO customer) {
        Integer id = (customer.firstName() + customer.lastName()).length();
        return new Company(companyApiClient.getCompanyName(id).name());
    }
}
