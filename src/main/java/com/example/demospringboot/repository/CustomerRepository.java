package com.example.demospringboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

  @Query("SELECT new com.example.demospringboot.repository.CustomerResponseDTO(u.uuid, u.firstName, u.lastName) " +
          "FROM Customer u " +
          "WHERE u.uuid= :uuid ")
  CustomerResponseDTO findCust(UUID uuid);
}
