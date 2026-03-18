package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.AuthorDto;
import com.ludvig.libraryapi.entity.Author;
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
        Author author = authorRepository.findById(id).orElseThrow();
        return new AuthorDto(author.getId(), author.getName());
    }
}