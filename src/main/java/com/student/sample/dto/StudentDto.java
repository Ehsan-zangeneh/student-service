package com.student.sample.dto;

import com.student.sample.model.Major;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class StudentDto {
    String name;
    String family;
    String birthPlace;
    Major field;
    LocalDateTime createdDate;
    String nationalCode;
    LocalDateTime registeredDate;
}
