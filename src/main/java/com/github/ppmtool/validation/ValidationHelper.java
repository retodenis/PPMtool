package com.github.ppmtool.validation;

import com.github.ppmtool.domain.errors.ErrorDto;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationHelper {
    public static Map<String, List<ErrorDto>> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        fieldError -> fieldError.getField(),
                        Collectors.mapping(
                                fieldError -> new ErrorDto(fieldError.getDefaultMessage()), Collectors.toList()
                        )));
    }
}
