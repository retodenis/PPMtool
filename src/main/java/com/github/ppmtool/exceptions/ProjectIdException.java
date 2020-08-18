package com.github.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIdException extends RuntimeException {

    public ProjectIdException(String message) {
        super(message);
    }

    public ProjectIdException(String message, String... errors) {
         super(MessageFormat.format(message, errors));
    }
}
