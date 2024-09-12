package de.lumiliaro.symptothermapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import de.lumiliaro.symptothermapp.dto.ErrorDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Interner Serverfehler", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    public ResponseEntity<ErrorDto> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                "ERR_GLOBAL",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Ressource nicht gefunden", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    ResponseEntity<ErrorDto> itemNotFoundHandler(ItemNotFoundException ex, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                "ERR_NOT_FOUND",
                HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
        // ProblemDetail problemDetail = ProblemDetail
        // .forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        // problemDetail.setTitle(String.format("%s nicht gefunden: ",
        // e.getResource()));
        // problemDetail.setProperty("errorCategory", "Generic");
        // problemDetail.setProperty("timestamp", Instant.now());

        // return problemDetail;
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    @ApiResponse(responseCode = "409", description = "Ressource existiert bereits", content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    ResponseEntity<ErrorDto> itemAlreadyExistsHandler(ItemAlreadyExistsException ex, WebRequest request) {

        ErrorDto errorDto = new ErrorDto(
                ex.getMessage(),
                "ERR_ALREADY_EXISTS",
                HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
        // ProblemDetail problemDetail = ProblemDetail
        // .forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        // problemDetail.setTitle("Datensatz existiert bereits");
        // problemDetail.setProperty("errorCategory", "Generic");
        // problemDetail.setProperty("timestamp", Instant.now());

        // return problemDetail;
    }

}
