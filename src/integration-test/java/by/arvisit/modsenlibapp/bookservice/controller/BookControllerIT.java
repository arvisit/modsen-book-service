package by.arvisit.modsenlibapp.bookservice.controller;

import static by.arvisit.modsenlibapp.bookservice.util.BookITData.BOOK_SPRING_MICROSERVICES_ID;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.BOOK_SPRING_MICROSERVICES_ISBN;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.URL_BOOKS_ENDPOINT;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.URL_BOOK_BY_ID_TEMPLATE;
import static by.arvisit.modsenlibapp.bookservice.util.BookITData.getAdmin;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import by.arvisit.modsenlibapp.bookservice.PostgreSQLTestContainerExtension;
import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.dto.LibraryBookDto;
import by.arvisit.modsenlibapp.bookservice.util.BookITData;
import by.arvisit.modsenlibapp.innerfilterstarter.dto.UserDto;

@ActiveProfiles("itest")
@ExtendWith(PostgreSQLTestContainerExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = "classpath:sql/add-books.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:sql/delete-books.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) })
@WireMockTest(httpPort = 8480)
class BookControllerIT {

    private static final String USERS_VALIDATE_URL = "/api/v1/users/validate";
    private static final String ADD_NEW_BOOK_URL = "/api/v1/library/books";
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final int EXPECTED_BOOK_COUNT_DEFAULT = 3;
    private static final int EXPECTED_BOOKS_COUNT_AFTER_DELETE = 2;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

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
    void shouldReturn201AndJsonContentType_when_invokeSave() throws Exception {
        BookRequestDto requestBody = BookITData.getRequestToSaveHeadFirstJava().build();

        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        LibraryBookDto libraryBook = new LibraryBookDto(BOOK_SPRING_MICROSERVICES_ID);
        wireMockResponseFromLibraryService(libraryBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOKS_ENDPOINT,
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldReturnExpectedResponse_when_invokeSave() throws Exception {
        BookRequestDto requestBody = BookITData.getRequestToSaveHeadFirstJava().build();

        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        LibraryBookDto libraryBook = new LibraryBookDto(BOOK_SPRING_MICROSERVICES_ID);
        wireMockResponseFromLibraryService(libraryBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);

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
    void shouldReturn200AndJsonContentType_when_invokeUpdate() throws Exception {
        BookRequestDto requestBody = BookITData.getRequestToUpdateSpringMicroservices().build();

        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);

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
    void shouldReturnExpectedResponse_when_invokeUpdate() throws Exception {
        BookRequestDto requestBody = BookITData.getRequestToUpdateSpringMicroservices().build();

        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<BookRequestDto> requestEntity = new HttpEntity<>(requestBody, headers);

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
    void shouldReturn204_when_invokeDelete() throws Exception {
        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.DELETE,
                requestEntity,
                String.class,
                BOOK_SPRING_MICROSERVICES_ID);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void shouldReturnAllBooksMinusOne_when_invokeDeleteAndThenInvokeGetBooks() throws Exception {
        UserDto user = getAdmin();
        wireMockResponseFromSecurityService(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(JWT_TOKEN);
        HttpEntity<Void> deleteRequestEntity = new HttpEntity<>(headers);

        restTemplate.exchange(URL_BOOK_BY_ID_TEMPLATE,
                HttpMethod.DELETE,
                deleteRequestEntity,
                Void.class,
                BOOK_SPRING_MICROSERVICES_ID);

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

    private void wireMockResponseFromSecurityService(UserDto user) throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(user);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(USERS_VALIDATE_URL))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)));
    }
    
    private void wireMockResponseFromLibraryService(LibraryBookDto dto) throws JsonProcessingException {
        String body = objectMapper.writeValueAsString(dto);
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(ADD_NEW_BOOK_URL))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(body)));
    }
}
