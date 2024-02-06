package by.arvisit.modsenlibapp.bookservice.util;

import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Genre;

public final class GenreTestData {

    public static final String DEFAULT_GENRE_NAME = "Computer Science";

    private GenreTestData() {
    }

    public static Genre.GenreBuilder getDefaultGenre() {
        return Genre.builder()
                .withId(1L)
                .withName(DEFAULT_GENRE_NAME);
    }

    public static GenreDto.GenreDtoBuilder getDefaultGenreDto() {
        return GenreDto.builder()
                .withId(1L)
                .withName(DEFAULT_GENRE_NAME);
    }
}
