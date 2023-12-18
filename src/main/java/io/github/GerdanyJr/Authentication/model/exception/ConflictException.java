package io.github.GerdanyJr.Authentication.model.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends BaseException {
    public ConflictException(String msg) {
        super(msg, HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT);
    }
}
