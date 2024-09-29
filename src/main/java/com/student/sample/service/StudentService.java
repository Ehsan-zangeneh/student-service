package com.student.sample.service;

import com.student.sample.dto.StudentDto;
import com.student.sample.model.Student;
import com.student.sample.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    public void save(StudentDto studentDto) {
         /*
            trace level is usually not used.
            here it is active only for the dev profile
        */
        log.trace("Method save called");
        /*
          active for dev and test profile
        */
        log.debug("Received parameter for method save {}",studentDto);
        studentRepository.save(Student.builder()
                        .name(studentDto.getName())
                        .family(studentDto.getFamily())
                        .birthPlace(studentDto.getBirthPlace())
                        .field(studentDto.getField())
                        .nationalCode(studentDto.getNationalCode())
                .build());
        log.trace("Method save ended");
    }

    public List<StudentDto> getAll() {
        /*
            trace level is usually not used.
            here it is active only for the dev profile
        */
        log.trace("Method getAll called");
        return studentRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    public Optional<StudentDto> getByNationalCode(String nationalCode) {
         /*
            trace level is usually not used.
            here it is active only for the dev profile
        */
        log.trace("Method getByNationalCode called");
        /*
          active for dev and test profile
        */
        log.debug("Received parameter for method getByNationalCode {}", nationalCode);
        if(Strings.isBlank(nationalCode))
            return Optional.empty();
        return studentRepository.findByNationalCode(nationalCode)
                .map(this::convert);
    }

    private StudentDto convert(Student student) {
        /*
            trace level is usually not used.
            here it is active only for the dev profile
        */
        log.trace("Method getAll called");
        /*
           active for dev and test profile
        */
        log.debug("Received parameter for method convert {}", student);
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
