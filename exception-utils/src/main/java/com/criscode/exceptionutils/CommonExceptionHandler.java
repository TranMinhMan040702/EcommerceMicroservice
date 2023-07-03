package com.criscode.exceptionutils;

import com.criscode.exceptionutils.error.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CommonExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleNotFound(final NotFoundException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleAlreadyExists(final AlreadyExistsException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.CONFLICT.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.CONFLICT.getReasonPhrase())
                .build();
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidData(final InvalidDataException ex, HttpServletRequest request) {
        return Error.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(ex.getMessage())
                .path(request.getRequestURI())
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .build();
    }
}
