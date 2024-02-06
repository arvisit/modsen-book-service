package by.arvisit.modsenlibapp.bookservice.service.impl;

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

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    
    @Transactional(readOnly = true)
    @Override
    public List<BookResponseDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::fromEntityToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponseDto getBookById(String id) {
        // TODO Catch EntityNotFoundException in GlobalExceptionHandler
        
        return bookMapper.fromEntityToDto(
                bookRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException("message"))); // TODO replace message and maybe rewrite method to use logging
    }

    @Transactional(readOnly = true)
    @Override
    public BookResponseDto getBookByIsbn(String isbn) {
        // TODO Catch EntityNotFoundException in GlobalExceptionHandler
        
        return bookMapper.fromEntityToDto(
                bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("message"))); // TODO replace message and maybe rewrite method to use logging
    }

    @Transactional
    @Override
    public BookResponseDto save(BookRequestDto dto) {
        Book savedEntity = bookRepository.save(
                bookMapper.fromDtoToEntity(dto));
        return bookMapper.fromEntityToDto(savedEntity);
    }

    @Transactional
    @Override
    public BookResponseDto update(String id, BookRequestDto dto) {
        Book existingBook = bookRepository.findById(UUID.fromString(id)).orElseThrow(() -> new EntityNotFoundException("message")); // TODO replace message
        
        bookMapper.updateEntityWithDto(dto, existingBook);
        return bookMapper.fromEntityToDto(
                bookRepository.save(existingBook));
    }

    @Transactional
    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        bookRepository.deleteById(UUID.fromString(id));

    }

}
