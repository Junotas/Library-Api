package com.ludvig.libraryapi.mapper;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
import com.ludvig.libraryapi.entity.Author;

public class AuthorMapper {

  public static AuthorDto toDto(Author author) {
    return new AuthorDto(
        author.getId(), author.getName(), author.getBooks() != null ? author.getBooks().size() : 0);
  }

  public static Author toEntity(AuthorRequest request) {
    Author author = new Author();
    author.setName(request.name());
    return author;
  }
}
