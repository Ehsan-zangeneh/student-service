package com.student.sample.service;

import com.student.sample.dto.StudentDto;
import com.student.sample.model.Student;
import com.student.sample.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(StudentDto studentDto) {
        studentRepository.save(Student.builder()
                        .name(studentDto.getName())
                        .family(studentDto.getFamily())
                        .birthPlace(studentDto.getBirthPlace())
                        .field(studentDto.getField())
                        .nationalCode(studentDto.getNationalCode())
                .build());
    }

    public List<StudentDto> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    public Optional<StudentDto> getByNationalCode(String nationalCode) {
        if(Strings.isBlank(nationalCode))
            return Optional.empty();
        return studentRepository.findByNationalCode(nationalCode)
                .map(this::convert);
    }

    private StudentDto convert(Student student) {
        return StudentDto.builder()
                .name(student.getName())
                .family(student.getFamily())
                .field(student.getField())
                .birthPlace(student.getBirthPlace())
                .createdDate(student.getCreatedDate())
                .registeredDate(student.getRegisteredDate())
                .nationalCode(student.getNationalCode())
                .build();
    }

}
