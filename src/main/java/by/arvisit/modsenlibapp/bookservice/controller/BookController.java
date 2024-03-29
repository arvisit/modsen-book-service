package by.arvisit.modsenlibapp.bookservice.controller;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.arvisit.modsenlibapp.bookservice.controller.openapi.BookOpenApi;
import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.service.BookService;
import by.arvisit.modsenlibapp.bookservice.validation.Isbn;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BookController implements BookOpenApi {

    private final BookService bookService;

    @Override
    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable @UUID String id) {
        BookResponseDto response = bookService.getBookById(id);
        log.debug("Got book with id {}: {}", id, response);
        return response;
    }

    @Override
    @GetMapping("/by-isbn/{isbn}")
    public BookResponseDto getBookByIsbn(@PathVariable @Isbn String isbn) {
        BookResponseDto response = bookService.getBookByIsbn(isbn);
        log.debug("Got book with ISBN {}: {}", isbn, response);
        return response;
    }

    @Override
    @GetMapping
    public List<BookResponseDto> getBooks() {
        List<BookResponseDto> response = bookService.getBooks();
        log.debug("Got all books. Total count: {}", response.size());
        return response;
    }

    @Override
    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<BookResponseDto> save(@RequestBody @Valid BookRequestDto request) {
        BookResponseDto response = bookService.save(request);
        log.debug("New book was added: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    public BookResponseDto update(@PathVariable @UUID String id, @RequestBody @Valid BookRequestDto bookToUpdate) {
        BookResponseDto response = bookService.update(id, bookToUpdate);
        log.debug("Book with id {} was updated and now is {}", id, response);
        return response;
    }

    @Override
    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> delete(@PathVariable @UUID String id) {
        bookService.delete(id);
        log.debug("Book with id {} was deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
