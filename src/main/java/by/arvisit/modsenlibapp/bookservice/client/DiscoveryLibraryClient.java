package by.arvisit.modsenlibapp.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import by.arvisit.modsenlibapp.bookservice.dto.LibraryBookDto;
import by.arvisit.modsenlibapp.innerfilterstarter.client.InnerCommunicationFeignConfiguration;

@Profile({ "docker" })
@FeignClient(value = "modsen-library-service", configuration = InnerCommunicationFeignConfiguration.class)
public interface DiscoveryLibraryClient extends LibraryClient {

    @Override
    @PostMapping("/api/v1/library/books")
    LibraryBookDto addNewBook(@RequestBody LibraryBookDto newBook);
}
