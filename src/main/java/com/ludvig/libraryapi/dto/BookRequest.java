package com.ludvig.libraryapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
    @NotBlank(message = "Title is required") String title,
    @NotNull(message = "Author ID is required") Long authorId) {}
