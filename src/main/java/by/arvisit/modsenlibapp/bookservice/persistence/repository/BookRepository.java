package by.arvisit.modsenlibapp.bookservice.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arvisit.modsenlibapp.bookservice.persistence.model.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Optional<Book> findByIsbn(String isbn);
}
