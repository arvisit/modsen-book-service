package by.arvisit.modsenlibapp.bookservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.arvisit.modsenlibapp.bookservice.persistence.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
