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
        @Isbn
        String isbn,
        @Length(max = 50, message = "Book title length should be not greater than {max}")
        @NotBlank(message = "Book title must be not null nor blank.")
        String title,
        @NotNull(message = "Book genre must be not null.")
        @IsGenreExist
        GenreDto genre,
        @NotBlank(message = "Book description must be not null nor blank.")
        @Length(max = 255, message = "Book description length should be not greater than {max}")
        String description,
        @NotBlank(message = "Book author must be not null nor blank.")
        @Length(max = 50, message = "Book description length should be not greater than {max}")
        String author) {

}
