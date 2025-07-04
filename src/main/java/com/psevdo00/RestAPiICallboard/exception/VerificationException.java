package com.psevdo00.RestAPiICallboard.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Getter
public class VerificationException extends ResponseStatusException {

    private final Map<String, String> errors;

    public VerificationException(HttpStatus status, Map<String, String> errors){

        super(status, errors.toString());
        this.errors = errors;

    }

}
