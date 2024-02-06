package by.arvisit.modsenlibapp.bookservice.controller;

import java.util.List;

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

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping("/{id}")
    public BookResponseDto getBookById(@PathVariable String id) {
        BookResponseDto response = bookService.getBookById(id);
        log.debug("Got book with id {}: {}", id, response);
        return response;
    }

    @GetMapping("/by-isbn/{isbn}")
    public BookResponseDto getBookByIsbn(@PathVariable String isbn) {
        BookResponseDto response = bookService.getBookByIsbn(isbn);
        log.debug("Got book with ISBN {}: {}", isbn, response);
        return response;
    }

    @GetMapping
    public List<BookResponseDto> getBooks() {
        List<BookResponseDto> response = bookService.getBooks();
        log.debug("Got all books: {}", response);
        return response;
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> save(@RequestBody @Valid BookRequestDto request) {
        BookResponseDto response = bookService.save(request);
        log.debug("New book was added: {}", response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BookResponseDto update(@PathVariable String id, @RequestBody @Valid BookRequestDto bookToUpdate) {
        BookResponseDto response = bookService.update(id, bookToUpdate);
        log.debug("Book with id {} was updated and now is {}", id, response);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookService.delete(id);
        log.debug("Book with id {} was deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
