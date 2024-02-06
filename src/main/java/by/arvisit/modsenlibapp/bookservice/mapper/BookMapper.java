package by.arvisit.modsenlibapp.bookservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = GenreMapper.class)
public interface BookMapper {

    BookResponseDto fromEntityToDto(Book entity);

    @Mapping(target = "id", ignore = true)
    Book fromDtoToEntity(BookRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntityWithDto(BookRequestDto dto, @MappingTarget Book entity);
}
