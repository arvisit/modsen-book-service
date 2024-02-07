package by.arvisit.modsenlibapp.bookservice.validation.provider;

import by.arvisit.modsenlibapp.bookservice.dto.GenreDto;
import by.arvisit.modsenlibapp.bookservice.service.GenreService;
import by.arvisit.modsenlibapp.bookservice.validation.IsGenreExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsGenreExistValidator implements ConstraintValidator<IsGenreExist, GenreDto> {

    private final GenreService genreService;

    @Override
    public boolean isValid(GenreDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return genreService.isGenreExists(value);
    }

}
