package com.example.demospringboot.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(@NotBlank String firstName, @NotBlank String lastName) {
}
