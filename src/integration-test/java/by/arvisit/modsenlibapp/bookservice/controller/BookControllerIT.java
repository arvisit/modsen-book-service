package by.arvisit.modsenlibapp.bookservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import by.arvisit.modsenlibapp.bookservice.PostgreSQLTestContainerExtension;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;

@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:sql/add-books.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:sql/delete-books.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) })
class BookControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturn200AndJsonContentType_when_invokeGetBooks() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/api/v1/books",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeGetBooks() {
        ResponseEntity<List<BookResponseDto>> responseEntity = restTemplate.exchange(
                "/api/v1/books", // TODO extract to constants somewhere
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });

        assertThat(responseEntity.getBody()).hasSize(3); // TODO add more significant check

    }
}
