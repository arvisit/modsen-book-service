package by.arvisit.modsenlibapp.bookservice.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.mapper.BookMapper;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Book;
import by.arvisit.modsenlibapp.bookservice.persistence.repository.BookRepository;
import by.arvisit.modsenlibapp.bookservice.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional(readOnly = true)
    @Override
    public List<BookResponseDto> getBooks() {
        log.debug("Call for BookService.getBooks()");
        return bookRepository.findAll().stream()
                .map(bookMapper::fromEntityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponseDto getBookById(String id) {
        log.debug("Call for BookService.getBookById() with id {}", id);
        return bookMapper.fromEntityToDto(
                bookRepository.findById(UUID.fromString(id))
                        .orElseThrow(
                                () -> new EntityNotFoundException(MessageFormat.format("Found no Book with id {}", id))));
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponseDto getBookByIsbn(String isbn) {
        log.debug("Call for BookService.getBookByIsbn() with isbn {}", isbn);
        return bookMapper.fromEntityToDto(
                bookRepository.findByIsbn(isbn)
                        .orElseThrow(
                                () -> new EntityNotFoundException(MessageFormat.format("Found no Book with isbn {}", isbn))));
    }

    @Transactional
    @Override
    public BookResponseDto save(BookRequestDto dto) {
        log.debug("Call for BookService.save() with dto {}", dto);
        Book savedEntity = bookRepository.save(
                bookMapper.fromDtoToEntity(dto));
        return bookMapper.fromEntityToDto(savedEntity);
    }

    @Transactional
    @Override
    public BookResponseDto update(String id, BookRequestDto dto) {
        log.debug("Call for BookService.update() with id {} and dto {}", id, dto);
        Book existingBook = bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Found no Book with id {}", id)));

        bookMapper.updateEntityWithDto(dto, existingBook);
        return bookMapper.fromEntityToDto(
                bookRepository.save(existingBook));
    }

    @Transactional
    @Override
    public void delete(String id) {
        log.debug("Call for BookService.delete with id {}", id);
        bookRepository.deleteById(UUID.fromString(id));
    }

}
