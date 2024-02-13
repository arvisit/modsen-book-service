package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.web.bind.annotation.RequestBody;

import by.arvisit.modsenlibapp.bookservice.dto.LibraryBookDto;

public interface LibraryClient {

    LibraryBookDto addNewBook(@RequestBody LibraryBookDto newBook);

}
