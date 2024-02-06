package by.arvisit.modsenlibapp.bookservice.dto;

import org.hibernate.validator.constraints.Length;

import by.arvisit.modsenlibapp.bookservice.validation.IsGenreExist;
import by.arvisit.modsenlibapp.bookservice.validation.Isbn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record BookRequestDto(
        @NotBlank(message = "Book isbn must be not null nor blank.")
        @Isbn(message = "Book isbn should rather consist of 10 digits and 3 hyphens or 13 digits and 4 hyphens. Also the last character could be X. No hyphens concatenated. No hyphens at the beginnig and end.")
        String isbn,
        @Length(max = 50, message = "Book title length should be not greater then {max}")
        @NotBlank(message = "Book title must be not null nor blank.")
        String title,
        @NotNull(message = "Book genre must be not null.")
        @IsGenreExist(message = "Known genre should be used.")
        GenreDto genre,
        @NotBlank(message = "Book description must be not null nor blank.")
        @Length(max = 255, message = "Book description length should be not greater then {max}")
        String description,
        @NotBlank(message = "Book author must be not null nor blank.")
        @Length(max = 50, message = "Book description length should be not greater then {max}")
        String author) {

}
