package com.student.sample.service

import com.student.sample.common.integration.IntegrationSpec
import com.student.sample.dto.StudentDto
import com.student.sample.model.Major
import com.student.sample.model.Student
import com.student.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class StudentServiceIntegrationSpec extends IntegrationSpec {

    @Autowired
    StudentRepository repository

    @Subject
    @Autowired
    StudentService studentService;

    def"should return student based on the national code"() {
        given:
        def name = "name"
        def family = "family"
        def field = Major.MATHS
        def totalNumber = 10
        and: "insert a certain number of records in the DB"
        for(int i = 1; i <= totalNumber; i++) {
            repository.save(Student.builder()
                    .nationalCode(i+"")
                    .name(name + i)
                    .family(family + i)
                    .field(field)
                    .build())
        }


        when:
        def result = studentService.getByNationalCode(totalNumber + "")

        then:
        result.isPresent()
        result.get().any {
            it.nationalCode == totalNumber + ""
        }

    }


    def"should throw exception for duplicate national code"() {
        given:
        def name = "name"
        def family = "family"
        def field = Major.MATHS
        def totalNumber = 5
        and: "insert a certain number of records in the DB"
        for(int i = 1; i <= totalNumber; i++) {
            repository.save(Student.builder()
                    .nationalCode(i+"")
                    .name(name + i)
                    .family(family + i)
                    .field(field)
                    .build())
        }

        when:
        studentService.save(StudentDto.builder()
                                        .name(name)
                                        .family(family)
                                        .field(field)
                                        .nationalCode(totalNumber+"")
                                        .build())


        then:
            thrown(RuntimeException)

    }

}
