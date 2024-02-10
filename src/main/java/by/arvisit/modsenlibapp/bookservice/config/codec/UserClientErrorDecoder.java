package by.arvisit.modsenlibapp.bookservice.config.codec;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import by.arvisit.modsenlibapp.exceptionhandlingstarter.exception.BadRequestException;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.exception.InvalidTokenException;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.response.ExceptionResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class UserClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionResponse message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            message = objectMapper.readValue(bodyIs, ExceptionResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        if (message.status() == 400) {
            throw new BadRequestException(message.message());
        }
        if (message.status() == 401) {
            throw new InvalidTokenException(message.message());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

}
