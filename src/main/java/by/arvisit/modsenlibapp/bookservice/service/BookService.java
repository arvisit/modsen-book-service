package by.arvisit.modsenlibapp.bookservice.service;

import java.util.List;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;

public interface BookService {

    List<BookResponseDto> getBooks();

    BookResponseDto getBookById(String id);

    BookResponseDto getBookByIsbn(String isbn);

    BookResponseDto save(BookRequestDto dto);

    BookResponseDto update(String id, BookRequestDto dto);

    void delete(String id);
}
