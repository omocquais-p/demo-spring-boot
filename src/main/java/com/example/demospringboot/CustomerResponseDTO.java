package com.example.demospringboot;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("customer")
public record CustomerResponseDTO(@Id UUID uuid, String firstName, String name) {
}
