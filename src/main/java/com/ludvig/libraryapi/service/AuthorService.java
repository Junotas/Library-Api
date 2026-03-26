package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.dto.AuthorRequest;
import com.ludvig.libraryapi.entity.Author;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.mapper.AuthorMapper;
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
    return authorRepository.findAll().stream().map(AuthorMapper::toDto).toList();
  }

  public AuthorDto findById(Long id) {
    Author author = findEntityById(id);
    return AuthorMapper.toDto(author);
  }

  public Author findEntityById(Long id) {
    return authorRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
  }

  public AuthorDto save(AuthorRequest request) {
    Author author = AuthorMapper.toEntity(request);
    Author saved = authorRepository.save(author);
    return AuthorMapper.toDto(saved);
  }

  public AuthorDto update(Long id, AuthorRequest request) {
    Author author = findEntityById(id);
    author.setName(request.name());
    Author saved = authorRepository.save(author);
    return AuthorMapper.toDto(saved);
  }

  public void delete(Long id) {
    authorRepository.deleteById(id);
  }
}
