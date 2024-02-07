package by.arvisit.modsenlibapp.bookservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.service.BookService;
import by.arvisit.modsenlibapp.bookservice.service.GenreService;
import by.arvisit.modsenlibapp.bookservice.util.BookTestData;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.handler.GlobalExceptionHandlerAdvice;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandlerAdvice.class)
class BookControllerTest {

    private static final String INVALID_ISBN_MESSAGE = "Book isbn should rather consist of 10 digits and 3 hyphens or 13 digits and 4 hyphens. Also the last character could be X. No hyphens concatenated. No hyphens at the beginnig and end.";
    private static final String GENRE_NOT_EXIST_MESSAGE = "Known genre should be used.";
    private static final String INVALID_UUID_MESSAGE = "must be a valid UUID";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private GenreService genreService;

    @Nested
    class SaveBook {

        @Test
        void shouldReturn201_when_passValidInput() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void shouldMapsToBusinessModel_when_passValidInput() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();
            BookResponseDto responseDto = BookTestData.getDefaultBookResponseDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);
            Mockito.when(bookService.save(requestDto)).thenReturn(responseDto);

            MvcResult mvcResult = mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            Mockito.verify(bookService, Mockito.times(1)).save(requestDto);
            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            BookResponseDto result = objectMapper.readValue(actualResponseBody, BookResponseDto.class);

            Assertions.assertThat(result).isEqualTo(responseDto);
        }

        @Test
        void shouldReturnValidBook_when_passValidInput() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();
            BookResponseDto responseDto = BookTestData.getDefaultBookResponseDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);
            Mockito.when(bookService.save(requestDto)).thenReturn(responseDto);

            MvcResult mvcResult = mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertThat(actualResponseBody)
                    .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(responseDto));
        }

        @ParameterizedTest
        @MethodSource("by.arvisit.modsenlibapp.bookservice.controller.BookControllerTest#invalidTenDigitIsbn")
        void shouldReturn400_when_passInvalidTenDigitIsbn(String isbn) throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto()
                    .withIsbn(isbn)
                    .build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            String expectedContent = INVALID_ISBN_MESSAGE;

            mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }

        @ParameterizedTest
        @MethodSource("by.arvisit.modsenlibapp.bookservice.controller.BookControllerTest#invalidThirteenDigitIsbn")
        void shouldReturn400_when_passInvalidThirteenDigitIsbn(String isbn) throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto()
                    .withIsbn(isbn)
                    .build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            String expectedContent = INVALID_ISBN_MESSAGE;

            mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }

        @Test
        void shouldReturn400_when_passInvalidGenre() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(false);

            String expectedContent = GENRE_NOT_EXIST_MESSAGE;

            mockMvc.perform(post(BookTestData.URL_BOOKS_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }
    }

    @Nested
    class UpdateBook {

        @Test
        void shouldReturn200_when_passValidInput() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, BookTestData.DEFAULT_STRING_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void shouldMapsToBusinessModel_when_passValidInput() throws Exception {
            String bookId = BookTestData.DEFAULT_STRING_ID;
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();
            BookResponseDto responseDto = BookTestData.getDefaultBookResponseDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);
            Mockito.when(bookService.update(bookId, requestDto)).thenReturn(responseDto);

            MvcResult mvcResult = mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            Mockito.verify(bookService, Mockito.times(1)).update(bookId, requestDto);
            String actualResponseBody = mvcResult.getResponse().getContentAsString();
            BookResponseDto result = objectMapper.readValue(actualResponseBody, BookResponseDto.class);

            Assertions.assertThat(result).isEqualTo(responseDto);
        }

        @Test
        void shouldReturnValidBook_when_passValidInput() throws Exception {
            String bookId = BookTestData.DEFAULT_STRING_ID;
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();
            BookResponseDto responseDto = BookTestData.getDefaultBookResponseDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);
            Mockito.when(bookService.update(bookId, requestDto)).thenReturn(responseDto);

            MvcResult mvcResult = mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, bookId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            String actualResponseBody = mvcResult.getResponse().getContentAsString();

            Assertions.assertThat(actualResponseBody)
                    .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(responseDto));
        }

        @ParameterizedTest
        @MethodSource("by.arvisit.modsenlibapp.bookservice.controller.BookControllerTest#invalidTenDigitIsbn")
        void shouldReturn400_when_passInvalidTenDigitIsbn(String isbn) throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto()
                    .withIsbn(isbn)
                    .build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            String expectedContent = INVALID_ISBN_MESSAGE;

            mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, BookTestData.DEFAULT_STRING_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }

        @ParameterizedTest
        @MethodSource("by.arvisit.modsenlibapp.bookservice.controller.BookControllerTest#invalidThirteenDigitIsbn")
        void shouldReturn400_when_passInvalidThirteenDigitIsbn(String isbn) throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto()
                    .withIsbn(isbn)
                    .build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(true);

            String expectedContent = INVALID_ISBN_MESSAGE;

            mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, BookTestData.DEFAULT_STRING_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }

        @Test
        void shouldReturn400_when_passInvalidGenre() throws Exception {
            BookRequestDto requestDto = BookTestData.getDefaultBookRequestDto().build();

            Mockito.when(genreService.isGenreExists(Mockito.any())).thenReturn(false);

            String expectedContent = GENRE_NOT_EXIST_MESSAGE;

            mockMvc.perform(put(BookTestData.URL_BOOK_BY_ID_TEMPLATE, BookTestData.DEFAULT_STRING_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString(expectedContent)));
        }
    }

    static Stream<String> invalidTenDigitIsbn() {
        return Stream.of("-0123-456-789", "---0123456789", "0123-456-789-", "012-345--678-9", "01-23-4567X-9",
                "1-2-3-3", "012-3456-789-X");
    }

    static Stream<String> invalidThirteenDigitIsbn() {
        return Stream.of("-0123-4561-7812-9", "---0123456789012", "01231-4562-7893-", "0121-3452--6782-9",
                "011-2322-4567X-9", "1-2-3-3-4", "012-345-678-9012-1");
    }
}
