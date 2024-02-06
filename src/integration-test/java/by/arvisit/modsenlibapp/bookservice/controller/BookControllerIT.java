package by.arvisit.modsenlibapp.bookservice.controller;

import static by.arvisit.modsenlibapp.bookservice.util.BookITData.BOOK_SPRING_MICROSERVICES_ID;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.BOOK_SPRING_MICROSERVICES_ISBN;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.URL_BOOKS_ENDPOINT;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.URL_BOOK_BY_ID_TEMPLATE;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.getResponseForJavaPersistence;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.getResponseForLinuxCommandLine;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.getResponseForSpringMicroservices;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import by.arvisit.modsenlibapp.bookservice.PostgreSQLTestContainerExtension;
import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.util.BookITData;

@Profile("itest")
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:sql/add-books.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:sql/delete-books.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) })
class BookControllerIT {

    private static final int EXPECTED_BOOK_COUNT_DEFAULT = 3;
    private static final int EXPECTED_BOOKS_COUNT_AFTER_DELETE = 2;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturn200AndJsonContentType_when_invokeGetBooks() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.GET,
                requestEntity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeGetBooks() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;
        ResponseEntity<List<BookResponseDto>> responseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        List<BookResponseDto> expected = List.of(
                getResponseForSpringMicroservices().build(),
                getResponseForJavaPersistence().build(),
                getResponseForLinuxCommandLine().build());

        List<BookResponseDto> result = responseEntity.getBody();
        assertThat(result)
                .hasSize(EXPECTED_BOOK_COUNT_DEFAULT)
                .isEqualTo(expected);
    }

    @Test
    void shouldReturn200AndJsonContentType_when_invokeGetBookById() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class,
                BOOK_SPRING_MICROSERVICES_ID);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeGetBookById() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;

        ResponseEntity<BookResponseDto> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                BookResponseDto.class,
                BOOK_SPRING_MICROSERVICES_ID);
        
        BookResponseDto expected = getResponseForSpringMicroservices().build();

        assertThat(responseEntity.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturn200AndJsonContentType_when_invokeGetBookByIsbn() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                BookITData.URL_BOOK_BY_ISBN_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                String.class,
                BOOK_SPRING_MICROSERVICES_ISBN);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeGetBookByIsbn() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;

        ResponseEntity<BookResponseDto> responseEntity = restTemplate.exchange(
                BookITData.URL_BOOK_BY_ISBN_TEMPLATE,
                HttpMethod.GET,
                requestEntity,
                BookResponseDto.class,
                BOOK_SPRING_MICROSERVICES_ISBN);

        BookResponseDto expected = getResponseForSpringMicroservices().build();

        assertThat(responseEntity.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturn201AndJsonContentType_when_invokeSave() {
        BookRequestDto requestBody = BookITData.getRequestToSaveHeadFirstJava().build();

        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeSave() {
        BookRequestDto requestBody = BookITData.getRequestToSaveHeadFirstJava().build();

        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<BookResponseDto> responseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.POST,
                requestEntity,
                BookResponseDto.class);

        BookResponseDto expectedIgnoreId = BookITData.getResponseForHeadFirstJava().build();

        BookResponseDto result = responseEntity.getBody();
        assertThat(result.id()).isNotNull();
        assertThat(result.isbn()).isEqualTo(expectedIgnoreId.isbn());
        assertThat(result.title()).isEqualTo(expectedIgnoreId.title());
        assertThat(result.description()).isEqualTo(expectedIgnoreId.description());
        assertThat(result.author()).isEqualTo(expectedIgnoreId.author());
        assertThat(result.genre()).isEqualTo(expectedIgnoreId.genre());
    }

    @Test
    void shouldReturn200AndJsonContentType_when_invokeUpdate() {
        BookRequestDto requestBody = BookITData.getRequestToUpdateSpringMicroservices().build();

        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                String.class,
                BOOK_SPRING_MICROSERVICES_ID);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeUpdate() {
        BookRequestDto requestBody = BookITData.getRequestToUpdateSpringMicroservices().build();

        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody);

        ResponseEntity<BookResponseDto> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.PUT,
                requestEntity,
                BookResponseDto.class,
                BOOK_SPRING_MICROSERVICES_ID);

        BookResponseDto expected = getResponseForSpringMicroservices()
                .withIsbn(requestBody.isbn())
                .withTitle(requestBody.title())
                .withDescription(requestBody.description())
                .withGenre(requestBody.genre())
                .withAuthor(requestBody.author())
                .build();

        assertThat(responseEntity.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturn204_when_invokeDelete() {
        HttpEntity<?> requestEntity = HttpEntity.EMPTY;

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.DELETE,
                requestEntity,
                String.class,
                BOOK_SPRING_MICROSERVICES_ID);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnAllBooksMinusOne_when_invokeDeleteAndThenInvokeGetBooks() {
        String requestBookId = getResponseForSpringMicroservices().build().id();
        restTemplate.delete(URL_BOOK_BY_ID_TEMPLATE, requestBookId);

        HttpEntity<?> remainingBooksRequestEntity = HttpEntity.EMPTY;
        ResponseEntity<List<BookResponseDto>> remainingBooksResponseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.GET,
                remainingBooksRequestEntity,
                new ParameterizedTypeReference<>() {
                });

        List<BookResponseDto> expected = List.of(
                getResponseForJavaPersistence().build(),
                getResponseForLinuxCommandLine().build());

        List<BookResponseDto> result = remainingBooksResponseEntity.getBody();
        assertThat(result)
                .hasSize(EXPECTED_BOOKS_COUNT_AFTER_DELETE)
                .isEqualTo(expected);
    }
}
