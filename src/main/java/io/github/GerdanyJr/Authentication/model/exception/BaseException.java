package io.github.GerdanyJr.Authentication.model.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private Integer code;
    private HttpStatus httpStatus;

    public BaseException(String msg, Integer code, HttpStatus httpStatus) {
        super(msg);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
