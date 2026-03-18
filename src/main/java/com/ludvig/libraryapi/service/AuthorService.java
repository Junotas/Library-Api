package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
import com.ludvig.libraryapi.entity.Author;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public List<AuthorDto> findAll() {
    return authorRepository.findAll().stream()
        .map(author -> new AuthorDto(author.getId(), author.getName()))
        .toList();
  }

  public AuthorDto findById(Long id) {
    Author author =
        authorRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    return new AuthorDto(author.getId(), author.getName());
  }

  public AuthorDto save(AuthorRequest request) {
    Author author = new Author();
    author.setName(request.name());
    Author saved = authorRepository.save(author);
    return new AuthorDto(saved.getId(), saved.getName());
  }

  public AuthorDto update(Long id, AuthorRequest request) {
    Author author =
        authorRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    author.setName(request.name());
    Author saved = authorRepository.save(author);
    return new AuthorDto(saved.getId(), saved.getName());
  }

  public void delete(Long id) {
    authorRepository.deleteById(id);
  }
}
