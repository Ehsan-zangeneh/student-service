package com.student.sample.service

import com.student.sample.dto.StudentDto
import com.student.sample.model.Major
import com.student.sample.model.Student
import com.student.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class StudentServiceIntegrationSpec extends Specification {

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

        cleanup:
        repository.deleteAll()

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

        cleanup:
        repository.deleteAll()

    }

}
