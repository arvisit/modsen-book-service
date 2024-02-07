package by.arvisit.modsenlibapp.bookservice.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.mapper.GenreMapper;
import by.arvisit.modsenlibapp.bookservice.persistence.repository.GenreRepository;
import by.arvisit.modsenlibapp.bookservice.service.GenreService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public boolean isGenreExists(GenreDto dto) {
        return genreRepository.exists(Example.of(genreMapper.fromDtoToEntity(dto)));
    }

}
