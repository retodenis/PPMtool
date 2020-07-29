package com.github.ppmtool.domain.errors;

import lombok.Data;

import java.util.UUID;

@Data
public class ErrorDto {
    private String errorId;
    private String errorName;

    public ErrorDto(String errorName) {
        this.errorId = UUID.randomUUID().toString();
        this.errorName = errorName;
    }
}
