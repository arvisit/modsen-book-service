package by.arvisit.modsenlibapp.bookservice.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.persistence.model.Genre;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, 
        builder = @Builder(disableBuilder = true))
public interface GenreMapper {

    GenreDto fromEntityToDto(Genre dto);

    Genre fromDtoToEntity(GenreDto dto);
}
