package com.flz.exception.handler;

import com.flz.exception.AbstractAlreadyExistsException;
import com.flz.exception.AbstractNotFoundException;
import com.flz.model.response.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        log.error(exception.getMessage(), exception);
        String fieldName = null;
        String errorMessage = "Validation failed.";

        if (exception.getBindingResult().hasFieldErrors()) {
            FieldError fieldError = exception.getBindingResult().getFieldError();
            if (fieldError != null) {
                fieldName = fieldError.getField();
                errorMessage = fieldError.getDefaultMessage();
            }
        }

        return ErrorResponse.builder()
                .message(errorMessage)
                .field(fieldName)
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception.getErrorCode());
        return ErrorResponse.builder()
                .message("This field is required to be filled.")
                .field(exception.getName())
                .build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException exception) {
        log.error(exception.getMessage(), exception.getCause());
        return ErrorResponse.builder()
                .message("Missing or invalid parameter:" + exception.getParameterName())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleIllegalArgumentException(final IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception.getCause());
        return ErrorResponse.builder()
                .message("This field must not be null or empty")
                .build();
    }

    @ExceptionHandler(AbstractNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleAbstractNotFoundException(final AbstractNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleSQLException(final SQLException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message("Database Error")
                .build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message("Database Error")
                .build();
    }

    @ExceptionHandler(AbstractAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handleAbstractAlreadyExistsException(final AbstractAlreadyExistsException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .message("Prepared for unpredictable errors.")
                .build();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleHandlerMethodValidationException(final HandlerMethodValidationException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .field(exception.getReason())
                .message("This field value is not Valid")
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.builder()
                .field(exception.getMessage())
                .message("This field is not Valid")
                .build();
    }

}
