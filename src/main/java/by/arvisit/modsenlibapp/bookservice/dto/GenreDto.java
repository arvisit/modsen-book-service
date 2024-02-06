package by.arvisit.modsenlibapp.bookservice.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GenreDto(
        @NotNull(message = "Genre id must be not null.")
        Long id,
        @NotBlank(message = "Genre name must be not null nor blank.")
        @Length(max = 50, message = "Genre name length should be not greater then {max}")
        String name) {

}
