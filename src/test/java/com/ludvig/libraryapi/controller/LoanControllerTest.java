package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoanControllerTest {

  @Value("${server.port}")
  private int port;

  private final RestTemplate restTemplate = new RestTemplate();
  private Long bookId;
  private Long borrowerId;

  @BeforeEach
  void setUp() {
    AuthorRequest authorRequest = new AuthorRequest("Test Author");
    ResponseEntity<AuthorDto> author =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/authors", authorRequest, AuthorDto.class);

    BookRequest bookRequest = new BookRequest("Test Book", author.getBody().id());
    ResponseEntity<BookDto> book =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/books", bookRequest, BookDto.class);
    bookId = book.getBody().id();

    BorrowerRequest borrowerRequest = new BorrowerRequest("Test Borrower", "test@example.com");
    ResponseEntity<BorrowerDto> borrower =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/borrowers", borrowerRequest, BorrowerDto.class);
    borrowerId = borrower.getBody().id();
  }

  @Test
  void shouldReturnEmptyListWhenNoLoans() {
    ResponseEntity<LoanDto[]> response =
        restTemplate.getForEntity("http://localhost:" + port + "/loans", LoanDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void shouldCreateLoan() {
    LoanRequest request = new LoanRequest(LocalDate.now(), bookId, borrowerId);

    ResponseEntity<LoanDto> response =
        restTemplate.postForEntity("http://localhost:" + port + "/loans", request, LoanDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody().bookId()).isEqualTo(bookId);
  }

  @Test
  void shouldGetLoanById() {
    LoanRequest request = new LoanRequest(LocalDate.now(), bookId, borrowerId);
    ResponseEntity<LoanDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/loans", request, LoanDto.class);

    ResponseEntity<LoanDto> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/loans/" + created.getBody().id(), LoanDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().id()).isEqualTo(created.getBody().id());
  }

  @Test
  void shouldUpdateLoan() {
    LoanRequest request = new LoanRequest(LocalDate.now(), bookId, borrowerId);
    ResponseEntity<LoanDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/loans", request, LoanDto.class);

    LoanRequest updateRequest = new LoanRequest(LocalDate.now().plusDays(7), bookId, borrowerId);
    ResponseEntity<LoanDto> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/loans/" + created.getBody().id(),
            HttpMethod.PUT,
            new HttpEntity<>(updateRequest),
            LoanDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().loanDate()).isEqualTo(LocalDate.now().plusDays(7));
  }

  @Test
  void shouldDeleteLoan() {
    LoanRequest request = new LoanRequest(LocalDate.now(), bookId, borrowerId);
    ResponseEntity<LoanDto> created =
        restTemplate.postForEntity("http://localhost:" + port + "/loans", request, LoanDto.class);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/loans/" + created.getBody().id(),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            Void.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
