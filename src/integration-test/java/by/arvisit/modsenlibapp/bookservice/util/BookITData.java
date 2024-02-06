package by.arvisit.modsenlibapp.bookservice.util;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;

public final class BookITData {

    public static final String URL_BOOKS_ENDPOINT = "/api/v1/books";
    public static final String URL_BOOK_BY_ID_TEMPLATE = "/api/v1/books/{id}";
    public static final String URL_BOOK_BY_ISBN_TEMPLATE = "/api/v1/books/isbn/{isbn}";

    private BookITData() {
    }

    public static BookResponseDto.BookResponseDtoBuilder getBookFromDBSpringMicroservices() {
        return BookResponseDto.builder()
                .withId("f4b27fe4-0ea1-423d-add6-daa2ee63802f")
                .withIsbn("978-1-617-29695-6")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("Spring Microservices in Action")
                .withDescription("Guide to Spring Cloud for microservices development")
                .withAuthor("Illary Huaylupo Sanchez");
    }
    
    public static BookRequestDto.BookRequestDtoBuilder getBookToUpdateSpringMicroservices() {
        return BookRequestDto.builder()
                .withIsbn("978-1-617-29695-6")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("Spring Microservices in Action, Second Edition")
                .withDescription("Guide to Spring Cloud for microservices development")
                .withAuthor("John Carnell");
    }

    public static BookResponseDto.BookResponseDtoBuilder getBookFromDBJavaPersistence() {
        return BookResponseDto.builder()
                .withId("c79eb50b-2e71-4e98-87ab-2074c7441713")
                .withIsbn("978-1-617-29045-9")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("Java Persistence with Hibernate")
                .withDescription("Guide to Java Persistence and ORM")
                .withAuthor("Christian Bauer");
    }

    public static BookResponseDto.BookResponseDtoBuilder getBookFromDBLinuxCommandLine() {
        return BookResponseDto.builder()
                .withId("0319cc17-a6e0-4bfd-a9ef-2ab5cca8abca")
                .withIsbn("978-5-4461-1430-6")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("The Linux Command Line")
                .withDescription("Guide to Linux command line")
                .withAuthor("William Shotts");
    }

    public static BookRequestDto.BookRequestDtoBuilder getBookToSaveHeadFirstJava() {
        return BookRequestDto.builder()
                .withIsbn("978-5-699-54574-2")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("Head First Java")
                .withDescription("Illustrated Guide to Java for beginners")
                .withAuthor("Kathy Sierra");
    }
    
    public static BookResponseDto.BookResponseDtoBuilder getNewBookFromDBHeadFirstJava() {
        return BookResponseDto.builder()
                .withId(null)
                .withIsbn("978-5-699-54574-2")
                .withGenre(getGenreFromDBComputerScience().build())
                .withTitle("Head First Java")
                .withDescription("Illustrated Guide to Java for beginners")
                .withAuthor("Kathy Sierra");
    }

    public static GenreDto.GenreDtoBuilder getGenreFromDBComputerScience() {
        return GenreDto.builder()
                .withId(1L)
                .withName("Computer Science");
    }

}
