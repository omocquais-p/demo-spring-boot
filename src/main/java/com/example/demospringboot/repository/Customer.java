package com.example.demospringboot.repository;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

  @Id
  @Column(name = "ID")
  @UuidGenerator
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  public UUID getUuid() {
    return uuid;
  }

  public Customer setUuid(UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public Customer setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Customer setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

}
