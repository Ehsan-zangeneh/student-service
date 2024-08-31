package com.student.sample.controller;

import com.student.sample.dto.StudentDto;
import com.student.sample.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody StudentDto studentDto) {
        studentService.save(studentDto);
    }

    @GetMapping("/getAll")
    public List<StudentDto> getAll() {
        return studentService.getAll();
    }

    @GetMapping("getByNationalCode")
    public StudentDto findByNationalCode(@RequestParam String nationalCode) {
        return studentService.getByNationalCode(nationalCode)
                .orElseGet(() -> StudentDto.builder().build());
    }

}
