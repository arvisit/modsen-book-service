package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import by.arvisit.modsenlibapp.bookservice.dto.UserDto;

@Profile({ "docker", "!itest" })
@FeignClient(value = "modsen-security-service")
public interface DiscoveryUserClient extends UserClient {

    @Override
    @GetMapping("/api/v1/users/validate")
    UserDto validate(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader);
}
