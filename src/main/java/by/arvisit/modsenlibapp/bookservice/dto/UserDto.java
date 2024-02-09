package by.arvisit.modsenlibapp.bookservice.dto;

import java.util.Collection;

public record UserDto(String username, Collection<String> authorities) {

}
