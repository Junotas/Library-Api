package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.dto.BookRequest;
import com.ludvig.libraryapi.entity.Author;
import com.ludvig.libraryapi.entity.Book;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.mapper.BookMapper;
import com.ludvig.libraryapi.repository.BookRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorService authorService;

  public BookService(BookRepository bookRepository, @Lazy AuthorService authorService) {
    this.bookRepository = bookRepository;
    this.authorService = authorService;
  }

  public List<BookDto> findAll() {
    return bookRepository.findAll().stream().map(BookMapper::toDto).toList();
  }

  public BookDto findById(Long id) {
    Book book = findEntityById(id);
    return BookMapper.toDto(book);
  }

  public Book findEntityById(Long id) {
    return bookRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
  }

  public BookDto save(BookRequest request) {
    Author author = authorService.findEntityById(request.authorId());
    Book book = BookMapper.toEntity(request, author);
    Book saved = bookRepository.save(book);
    return BookMapper.toDto(saved);
  }

  public BookDto update(Long id, BookRequest request) {
    Book book = findEntityById(id);
    Author author = authorService.findEntityById(request.authorId());
    book.setTitle(request.title());
    book.setAuthor(author);
    Book saved = bookRepository.save(book);
    return BookMapper.toDto(saved);
  }

  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
