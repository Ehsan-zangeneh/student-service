package com.student.sample.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        var message = alignMessages( ex.getBindingResult().getAllErrors());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private String alignMessages(List<ObjectError> errorList) {
        List<String> errorMessages = new ArrayList<>();
        errorList.forEach(
                objectError -> {
                    String field = ((FieldError)objectError).getField();
                    String defaultMessage = objectError.getDefaultMessage();
                    errorMessages.add("[%s:%s]".formatted(field,defaultMessage));
                }
        );
        return String.join(",", errorMessages);
    }




}
