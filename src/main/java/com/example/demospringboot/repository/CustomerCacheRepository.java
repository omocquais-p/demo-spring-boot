package com.example.demospringboot.repository;

import com.example.demospringboot.CustomerResponseDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerCacheRepository extends CrudRepository<CustomerResponseDTO, UUID> {
}
