package io.github.GerdanyJr.Authentication.model.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public record ErrorResponse(String message,
                HttpStatus status,
                Integer code,
                @JsonSerialize(using = LocalDateTimeSerializer.class) 
                @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime timeStamp) {

}
