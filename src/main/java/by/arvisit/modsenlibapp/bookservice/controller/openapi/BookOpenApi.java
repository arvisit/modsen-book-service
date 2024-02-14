package by.arvisit.modsenlibapp.bookservice.controller.openapi;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.hibernate.validator.constraints.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import by.arvisit.modsenlibapp.bookservice.dto.BookRequestDto;
import by.arvisit.modsenlibapp.bookservice.dto.BookResponseDto;
import by.arvisit.modsenlibapp.bookservice.validation.Isbn;
import by.arvisit.modsenlibapp.exceptionhandlingstarter.response.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Book Controller", description = "API for managing and obtaining book info")
public interface BookOpenApi {

    String BAD_REQUEST = "BAD REQUEST";
    String UNAUTHORIZED = "UNAUTHORIZED";
    String OK = "OK";
    String CREATED = "CREATED";
    String FORBIDDEN = "FORBIDDEN";
    String NOT_FOUND = "NOT FOUND";
    String NO_CONTENT = "NO CONTENT";

    @Operation(
            summary = "Book info obtainment by id",
            description = "Obtains book info by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = OK,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400", description = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "404", description = NOT_FOUND,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            })
    BookResponseDto getBookById(@Parameter(description = "Book of interest identificator",
            required = true,
            example = "d4cee2e6-cb52-4a17-ae47-f4ad85dd8b86") @PathVariable @UUID String id);

    @Operation(
            summary = "Book info obtainment by id",
            description = "Obtains book info by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = OK,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400", description = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "404", description = NOT_FOUND,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            })
    BookResponseDto getBookByIsbn(@Parameter(description = "Book of interest ISBN",
            required = true,
            example = "978-0-306-40615-7") @PathVariable @Isbn String isbn);

    @Operation(
            summary = "All books info obtainment",
            description = "Obtains info about all books in the catalog",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = OK,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookResponseDto.class)))
            })
    List<BookResponseDto> getBooks();

    @Operation(
            summary = "Adding a new book",
            description = "Addin a new book to the catalog. Admin role is required",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = CREATED,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400", description = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "401", description = UNAUTHORIZED,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "403", description = FORBIDDEN,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            })
    ResponseEntity<BookResponseDto> save(@Parameter(description = "New book info",
            required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(
                            implementation = BookRequestDto.class))) @RequestBody @Valid BookRequestDto request);

    @Operation(
            summary = "Updating an existing book",
            description = "Updating an existing book info. Admin role is required",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = OK,
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookResponseDto.class))),
                    @ApiResponse(
                            responseCode = "400", description = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "401", description = UNAUTHORIZED,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "403", description = FORBIDDEN,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "404", description = NOT_FOUND,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            })
    BookResponseDto update(@Parameter(description = "Book of interest identificator",
            required = true,
            example = "d4cee2e6-cb52-4a17-ae47-f4ad85dd8b86") @PathVariable @UUID String id,
            @Parameter(description = "Updated book info",
                    required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = BookRequestDto.class))) @RequestBody @Valid BookRequestDto bookToUpdate);

    @Operation(
            summary = "Deleting of an existing book",
            description = "Deleting of an existing book info. Admin role is required",
            responses = {
                    @ApiResponse(
                            responseCode = "204", description = NO_CONTENT),
                    @ApiResponse(
                            responseCode = "400", description = BAD_REQUEST,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "401", description = UNAUTHORIZED,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "403", description = FORBIDDEN,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class))),
                    @ApiResponse(
                            responseCode = "404", description = NOT_FOUND,
                            content = @Content(mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ExceptionResponse.class)))
            })
    ResponseEntity<Void> delete(@Parameter(description = "Book of interest identificator",
            required = true,
            example = "d4cee2e6-cb52-4a17-ae47-f4ad85dd8b86") @PathVariable @UUID String id);

}