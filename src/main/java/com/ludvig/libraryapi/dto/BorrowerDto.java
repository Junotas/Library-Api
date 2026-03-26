package com.ludvig.libraryapi.dto;

public record BorrowerDto(Long id, String name, String email, int activeLoanCount) {}
