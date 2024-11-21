package com.flz.exception.handler;

import com.flz.exception.AbstractNotFoundException;
import com.flz.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleValidationErrors(final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage(), exception.getObjectName(), exception.getErrorCount());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleValidationErrors(final MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception.getErrorCode());
        return new ErrorResponse(exception.getMessage(), exception.getName());
    }

    @ExceptionHandler(AbstractNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotExistError(final AbstractNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleSQLError(final SQLException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse("Database Error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message("Prepared for unpredictable errors.")
                .timestamp(LocalDateTime.now())
                .isSuccess(false)
                .build();
    }

}
