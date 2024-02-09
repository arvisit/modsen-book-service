package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

import by.arvisit.modsenlibapp.bookservice.dto.UserDto;

public interface UserClient {

    UserDto validate(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader);
}
