package org.example.springproject.errorHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ClobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception e
    ) {
        logger.error("handleException: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDto(
                        "Error By Server",
                                e.getMessage(),
                                LocalDateTime.now()
                        ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseDto> handleExceptionNotFound(
            NoSuchElementException e
    ) {
        logger.error("handleException: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDto(
                        "Error Not Found",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponseDto> handleExceptionBadRequest(
            Exception e
    ) {
        logger.error("handleException: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        "Error Bad Request",
                        e.getMessage(),
                        LocalDateTime.now()
                ));
    }
}
