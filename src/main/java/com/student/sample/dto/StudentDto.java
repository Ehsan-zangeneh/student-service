package com.student.sample.dto;

import com.student.sample.model.Major;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;

@Builder
@Value
public class StudentDto {

    @NotBlank
    String name;
    @NotBlank
    String family;
    String birthPlace;
    Major field;
    @NotBlank
    @Size(min = 5, max = 10)
    @Pattern(regexp = "\\d+", message = "Must contain only digits")
    String nationalCode;
    LocalDateTime createdDate;
    LocalDateTime registeredDate;

}
