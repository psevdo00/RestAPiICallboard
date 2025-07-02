package com.psevdo00.RestAPiICallboard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Getter
public class CustomApiException extends ResponseStatusException {

    private final Map<String, String> errors;

    public CustomApiException(HttpStatus status, Map<String, String> errors){

        super(status, errors.toString());
        this.errors = errors;

    }

}
