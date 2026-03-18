package com.ludvig.libraryapi.service;

import com.ludvig.libraryapi.dto.BookDto;
import com.ludvig.libraryapi.dto.BookRequest;
import com.ludvig.libraryapi.entity.Author;
import com.ludvig.libraryapi.entity.Book;
import com.ludvig.libraryapi.exception.ResourceNotFoundException;
import com.ludvig.libraryapi.repository.AuthorRepository;
import com.ludvig.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public List<BookDto> findAll() {
    return bookRepository.findAll().stream()
        .map(book -> new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId()))
        .toList();
  }

  public BookDto findById(Long id) {
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getId());
  }

  public BookDto save(BookRequest request) {
    Author author =
        authorRepository
            .findById(request.authorId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Author not found with id: " + request.authorId()));
    Book book = new Book();
    book.setTitle(request.title());
    book.setAuthor(author);
    Book saved = bookRepository.save(book);
    return new BookDto(saved.getId(), saved.getTitle(), saved.getAuthor().getId());
  }

  public BookDto update(Long id, BookRequest request) {
    Book book =
        bookRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    Author author =
        authorRepository
            .findById(request.authorId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Author not found with id: " + request.authorId()));
    book.setTitle(request.title());
    book.setAuthor(author);
    Book saved = bookRepository.save(book);
    return new BookDto(saved.getId(), saved.getTitle(), saved.getAuthor().getId());
  }

  public void delete(Long id) {
    bookRepository.deleteById(id);
  }
}
