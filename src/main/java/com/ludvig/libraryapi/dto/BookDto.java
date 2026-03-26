package com.ludvig.libraryapi.dto;

public record BookDto(Long id, String title, Long authorId, String authorName) {}
