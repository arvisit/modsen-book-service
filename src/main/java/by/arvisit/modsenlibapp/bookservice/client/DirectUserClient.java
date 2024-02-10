package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import by.arvisit.modsenlibapp.bookservice.config.UserClientFeignConfiguration;
import by.arvisit.modsenlibapp.bookservice.dto.UserDto;

@Profile({ "itest"})
@FeignClient(value = "modsen-security-service", url = "${spring.settings.modsen-security-service.uri}",  configuration = UserClientFeignConfiguration.class)
public interface DirectUserClient extends UserClient {

    @Override
    @GetMapping("/validate")
    UserDto validate(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String authHeader);
}
