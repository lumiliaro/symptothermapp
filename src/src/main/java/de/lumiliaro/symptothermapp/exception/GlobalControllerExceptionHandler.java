package de.lumiliaro.symptothermapp.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    ProblemDetail itemNotFoundHandler(ItemNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problemDetail.setTitle(String.format("%s nicht gefunden: ", e.getResource()));
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(ItemAlreadyExistsException.class)
    ProblemDetail itemAlreadyExistsHandler(ItemAlreadyExistsException e) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());

        problemDetail.setTitle("Datensatz existiert bereits");
        problemDetail.setProperty("errorCategory", "Generic");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

}
