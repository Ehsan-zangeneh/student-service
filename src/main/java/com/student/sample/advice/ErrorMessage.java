package com.student.sample.advice;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Value
public class ErrorMessage {

    LocalDateTime date;
    String code;
    String reason;
    String message;

}
