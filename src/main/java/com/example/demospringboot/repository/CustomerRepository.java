package com.example.demospringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

  @Query("SELECT new com.example.demospringboot.repository.CustomerResponseDTO(u.uuid, u.firstName, u.lastName, u.companyName) " +
          "FROM Customer u " +
          "WHERE u.uuid= :uuid ")
  CustomerResponseDTO findCust(UUID uuid);
}
