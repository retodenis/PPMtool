package com.github.ppmtool.validation;

import com.github.ppmtool.domain.errors.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationHelper {
    private static Map<String, List<ErrorDto>> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        fieldError -> fieldError.getField(),
                        Collectors.mapping(
                                fieldError -> new ErrorDto(fieldError.getDefaultMessage()), Collectors.toList()
                        )));
    }

    public static ResponseEntity<Map<String, List<ErrorDto>>> getBadRequestErrors(BindingResult bindingResult) {
        return new ResponseEntity<>(ValidationHelper.getErrors(bindingResult), HttpStatus.BAD_REQUEST);
    }
}
