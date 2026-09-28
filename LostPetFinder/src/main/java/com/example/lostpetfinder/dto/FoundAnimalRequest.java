package com.example.lostpetfinder.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record FoundAnimalRequest(
        @NotBlank @Size(max = 40) String species,
        @Size(max = 80) String breed,
        @NotBlank @Size(max = 50) String color,
        @NotBlank @Size(max = 120) String foundLocation,
        @Size(max = 500) String description,
        @NotNull @Positive Long userId
) {}
