package com.github.ppmtool.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectResponseDto {
    private String projectIdentifier;
    private String message;
}
