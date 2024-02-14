package by.arvisit.modsenlibapp.bookservice.dto;

import org.hibernate.validator.constraints.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record LibraryBookDto(@UUID
        @Schema(description = "Book id", example = "d4cee2e6-cb52-4a17-ae47-f4ad85dd8b86")
        String id) {
}
