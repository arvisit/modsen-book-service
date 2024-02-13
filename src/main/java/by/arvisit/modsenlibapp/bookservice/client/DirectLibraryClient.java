package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import by.arvisit.modsenlibapp.bookservice.dto.LibraryBookDto;
import by.arvisit.modsenlibapp.innerfilterstarter.client.InnerCommunicationFeignConfiguration;

@Profile({ "itest" })
@FeignClient(value = "modsen-library-service", url = "${spring.settings.modsen-library-service.uri}", configuration = InnerCommunicationFeignConfiguration.class)
public interface DirectLibraryClient extends LibraryClient {

    @Override
    @PostMapping
    LibraryBookDto addNewBook(@RequestBody LibraryBookDto newBook);
}
