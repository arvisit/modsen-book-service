package by.arvisit.modsenlibapp.bookservice.mapper;

import static by.arvisit.modsenlibapp.bookservice.util.GenreTestData.getDefaultGenre;
import static by.arvisit.modsenlibapp.bookservice.util.GenreTestData.getDefaultGenreDto;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Genre;

class GenreMapperTest {

    private final GenreMapper genreMapper = Mappers.getMapper(GenreMapper.class);

    @Test
    void shouldMapCorrectly_when_invokeFromEntityToDto() {
        Genre entity = getDefaultGenre().build();
        GenreDto dto = getDefaultGenreDto().build();

        assertThat(genreMapper.fromEntityToDto(entity)).isEqualTo(dto);
    }

    @Test
    void shouldMapCorrectly_when_invokeFromDtoToEntity() {
        Genre entity = getDefaultGenre().build();
        GenreDto dto = getDefaultGenreDto().build();

        assertThat(genreMapper.fromDtoToEntity(dto)).isEqualTo(entity);
    }
}
