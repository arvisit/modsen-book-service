package by.arvisit.modsenlibapp.bookservice.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record BookResponseDto(String id, String isbn, String title, GenreDto genre, String description, String author) {

}
