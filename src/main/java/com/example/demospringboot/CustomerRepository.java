package com.example.demospringboot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

  @Query("SELECT new com.example.demospringboot.CustomerResponse(u.uuid, u.firstName, u.lastName) " +
          "FROM Customer u " +
          "WHERE u.uuid= :uuid ")
  CustomerResponse findCust(UUID uuid);
}
