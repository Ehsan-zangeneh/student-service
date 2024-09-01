package com.student.sample.controller;

import com.student.sample.dto.StudentDto;
import com.student.sample.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody StudentDto studentDto) {
        log.trace("Method register started");
        log.info("Received parameter for method register {}",studentDto);
        studentService.save(studentDto);
        log.trace("Method register Ended");
    }

    @GetMapping("/getAll")
    public List<StudentDto> getAll() {
        log.trace("Method getAll called");
        return studentService.getAll();
    }

    @GetMapping("getByNationalCode")
    public StudentDto findByNationalCode(@RequestParam String nationalCode) {
        log.trace("Method findByNationalCode called");
        log.info("Received parameter for method findByNationalCode {}",nationalCode);
        return studentService.getByNationalCode(nationalCode)
                .orElseGet(() -> StudentDto.builder().build());
    }

}
