package by.arvisit.modsenlibapp.bookservice.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record GenreDto(
        @NotNull(message = "Genre id must be not null.")
        @Schema(description = "Genre id", example = "1")
        Long id,
        @NotBlank(message = "Genre name must be not null nor blank.")
        @Length(max = 50, message = "Genre name length should be not greater then {max}")
        @Schema(description = "Genre name", example = "Computer Science")
        String name) {

}
