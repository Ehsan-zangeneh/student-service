package com.student.sample.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        var message = alignMessages( ex.getBindingResult().getAllErrors());
        return generateMessage(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleNotReadableException(HttpMessageNotReadableException ex) {
        var message = ex.getMessage();
        return generateMessage(message, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        String message = ex.getMessage();
        return generateMessage(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorMessage> generateMessage(String message, HttpStatus status) {
        log.error("Exception message {}", message);
        return new ResponseEntity<>(ErrorMessage.builder()
                .date(LocalDateTime.now())
                .message(message)
                .code(String.valueOf(status.value()))
                .reason(status.getReasonPhrase())
                .build(), status);
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
