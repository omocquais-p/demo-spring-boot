package com.example.demospringboot;

import java.util.UUID;

public record CustomerResponse(UUID uuid, String firstName, String name) {
}
