package by.arvisit.modsenlibapp.bookservice.mapper;

import static by.arvisit.modsenlibapp.bookservice.util.BookTestData.getDefaultBook;
import static by.arvisit.modsenlibapp.bookservice.util.BookTestData.getDefaultBookRequestDto;
import static by.arvisit.modsenlibapp.bookservice.util.BookTestData.getDefaultBookResponseDto;
import static by.arvisit.modsenlibapp.bookservice.util.GenreTestData.getDefaultGenre;
import static by.arvisit.modsenlibapp.bookservice.util.GenreTestData.getDefaultGenreDto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Book;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Genre;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    private static final String NEW_TITLE = "New title";

    @InjectMocks
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Mock
    private GenreMapper genreMapper;

    @Test
    void shouldMapCorrectly_when_invokeFromEntityToDto() {
        Genre genreEntity = getDefaultGenre().build();
        GenreDto genreDto = getDefaultGenreDto().build();

        Mockito.when(genreMapper.fromEntityToDto(genreEntity)).thenReturn(genreDto);

        Book entity = getDefaultBook().build();
        BookResponseDto dto = getDefaultBookResponseDto().build();

        Assertions.assertThat(bookMapper.fromEntityToDto(entity)).isEqualTo(dto);
    }

    @Test
    void shouldMapCorrectly_when_invokeFromDtoToEntity() {
        Genre genreEntity = getDefaultGenre().build();
        GenreDto genreDto = getDefaultGenreDto().build();

        Mockito.when(genreMapper.fromDtoToEntity(genreDto)).thenReturn(genreEntity);

        Book entity = getDefaultBook().withId(null).build();
        BookRequestDto dto = getDefaultBookRequestDto().build();

        Assertions.assertThat(bookMapper.fromDtoToEntity(dto)).isEqualTo(entity);
    }

    @Test
    void shouldMapCorrectly_when_invokeUpdateEntityWithDto() {
        Book entity = getDefaultBook().build();
        BookRequestDto dto = getDefaultBookRequestDto().withTitle(NEW_TITLE).build();

        bookMapper.updateEntityWithDto(dto, entity);

        Assertions.assertThat(entity.getTitle()).isEqualTo(NEW_TITLE);

    }
}
