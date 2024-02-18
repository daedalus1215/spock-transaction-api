package com.example.transactionsapi.controller;

import com.example.transactionsapi.exception.AccountNotFoundException;
import com.example.transactionsapi.exception.Error;
import com.example.transactionsapi.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionsApiExceptionHandler {

    @ExceptionHandler(value = {AccountNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleAccountNotFoundException(Exception ex) {
        return buildErrorResponse(ex);
    }

    private ErrorResponse buildErrorResponse(Exception ex) {
        final String type = ex.getClass().getSimpleName();
        final String message = ex.getMessage();

        return new ErrorResponse(new Error(type, message));
    }
}
