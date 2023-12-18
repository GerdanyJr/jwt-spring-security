package io.github.GerdanyJr.Authentication.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException {
    public NotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND);
    }
}
