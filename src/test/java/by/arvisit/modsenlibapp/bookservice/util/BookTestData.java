package by.arvisit.modsenlibapp.bookservice.util;

import static by.arvisit.modsenlibapp.bookservice.util.GenreTestData.getDefaultGenre;

import java.util.UUID;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Book;

public final class BookTestData {

    public static final String DEFAULT_AUTHOR = "Illary Huaylupo Sanchez";
    public static final String DEFAULT_DESCRIPTION = "Guide to Spring Cloud for microservices development";
    public static final String DEFAULT_TITLE = "Spring Microservices in Action";
    public static final String DEFAULT_ISBN = "978-1-617-29695-6";
    public static final String DEFAULT_STRING_ID = "f4b27fe4-0ea1-423d-add6-daa2ee63802f";
    public static final String INVALID_STRING_ID = "f4b27fe4-0ea1-423d-add6-daa2ee63802k";
    public static final UUID DEFAULT_UUID_ID = UUID.fromString(DEFAULT_STRING_ID);
    
    public static final String URL_BOOKS_ENDPOINT = "/api/v1/books";
    public static final String URL_BOOK_BY_ID_TEMPLATE = "/api/v1/books/{id}";
    public static final String URL_BOOK_BY_ISBN_TEMPLATE = "/api/v1/books/by-isbn/{isbn}";

    private BookTestData() {
    }

    public static Book.BookBuilder getDefaultBook() {
        return Book.builder()
                .withId(DEFAULT_UUID_ID)
                .withIsbn(DEFAULT_ISBN)
                .withTitle(DEFAULT_TITLE)
                .withDescription(DEFAULT_DESCRIPTION)
                .withGenre(getDefaultGenre().build())
                .withAuthor(DEFAULT_AUTHOR);
    }

    public static BookRequestDto.BookRequestDtoBuilder getDefaultBookRequestDto() {
        return BookRequestDto.builder()
                .withIsbn(DEFAULT_ISBN)
                .withTitle(DEFAULT_TITLE)
                .withDescription(DEFAULT_DESCRIPTION)
                .withGenre(GenreTestData.getDefaultGenreDto().build())
                .withAuthor(DEFAULT_AUTHOR);
    }

    public static BookResponseDto.BookResponseDtoBuilder getDefaultBookResponseDto() {
        return BookResponseDto.builder()
                .withId(DEFAULT_STRING_ID)
                .withIsbn(DEFAULT_ISBN)
                .withTitle(DEFAULT_TITLE)
                .withDescription(DEFAULT_DESCRIPTION)
                .withGenre(GenreTestData.getDefaultGenreDto().build())
                .withAuthor(DEFAULT_AUTHOR);
    }
}
