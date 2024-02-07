package by.arvisit.modsenlibapp.bookservice.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.service.BookService;
import by.arvisit.modsenlibapp.bookservice.service.GenreService;
import by.arvisit.modsenlibapp.bookservice.util.BookTestData;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.handler.GlobalExceptionHandlerAdvice;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandlerAdvice.class)
class BookControllerTest {

    private static final String INVALID_ISBN_MESSAGE = "Book isbn should rather consist of 10 digits and 3 hyphens or 13 digits and 4 hyphens. Also the last character could be X. No hyphens concatenated. No hyphens at the beginnig and end.";

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

        @ParameterizedTest
        @ValueSource(strings = { "-0123-456-789", "---0123456789", "0123-456-789-", "012-345--678-9", "01-23-4567X-9",
                "1-2-3-3", "012-3456-789-X" })
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
        @ValueSource(strings = { "-0123-4561-7812-9", "---0123456789012", "01231-4562-7893-", "0121-3452--6782-9",
                "011-2322-4567X-9", "1-2-3-3-4", "012-345-678-9012-1" })
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
    }
}
