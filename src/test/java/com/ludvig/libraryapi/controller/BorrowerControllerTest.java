package com.ludvig.libraryapi.controller;

import com.ludvig.libraryapi.dto.BorrowerDto;
import com.ludvig.libraryapi.dto.BorrowerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BorrowerControllerTest {

  @Value("${server.port}")
  private int port;

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  void shouldReturnEmptyListWhenNoBorrowers() {
    ResponseEntity<BorrowerDto[]> response =
        restTemplate.getForEntity("http://localhost:" + port + "/borrowers", BorrowerDto[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void shouldCreateBorrower() {
    BorrowerRequest request = new BorrowerRequest("John Doe", "john@example.com");

    ResponseEntity<BorrowerDto> response =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/borrowers", request, BorrowerDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody().name()).isEqualTo("John Doe");
  }

  @Test
  void shouldGetBorrowerById() {
    BorrowerRequest request = new BorrowerRequest("Jane Doe", "jane@example.com");
    ResponseEntity<BorrowerDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/borrowers", request, BorrowerDto.class);

    ResponseEntity<BorrowerDto> response =
        restTemplate.getForEntity(
            "http://localhost:" + port + "/borrowers/" + created.getBody().id(), BorrowerDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name()).isEqualTo("Jane Doe");
  }

  @Test
  void shouldUpdateBorrower() {
    BorrowerRequest request = new BorrowerRequest("Old Name", "old@example.com");
    ResponseEntity<BorrowerDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/borrowers", request, BorrowerDto.class);

    BorrowerRequest updateRequest = new BorrowerRequest("New Name", "new@example.com");
    ResponseEntity<BorrowerDto> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/borrowers/" + created.getBody().id(),
            HttpMethod.PUT,
            new HttpEntity<>(updateRequest),
            BorrowerDto.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().name()).isEqualTo("New Name");
  }

  @Test
  void shouldDeleteBorrower() {
    BorrowerRequest request = new BorrowerRequest("To Be Deleted", "delete@example.com");
    ResponseEntity<BorrowerDto> created =
        restTemplate.postForEntity(
            "http://localhost:" + port + "/borrowers", request, BorrowerDto.class);

    ResponseEntity<Void> response =
        restTemplate.exchange(
            "http://localhost:" + port + "/borrowers/" + created.getBody().id(),
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            Void.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
