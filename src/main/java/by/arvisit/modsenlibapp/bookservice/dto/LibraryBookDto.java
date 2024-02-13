package by.arvisit.modsenlibapp.bookservice.dto;

import org.hibernate.validator.constraints.UUID;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record LibraryBookDto(@UUID String id) {

}
