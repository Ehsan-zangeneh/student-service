package com.student.sample.service

import com.student.sample.dto.StudentDto
import com.student.sample.model.Major
import com.student.sample.model.Student
import com.student.sample.repository.StudentRepository
import spock.lang.Specification

class StudentServiceSpec extends Specification {

    def studentRepoMock = Mock(StudentRepository)
    def studentService = new StudentService(studentRepoMock)

    def "should call save method for student" () {
        given:
        def name = "name"
        def family = "family"
        def field = Major.MATHS
        def nationalCode= "123456"
        and:
        def studentDto = StudentDto.builder()
                .name(name)
                .family(family)
                .nationalCode(nationalCode)
                .field(field)
                .build()

        when:
        studentService.save(studentDto)

        then:
        1 * studentRepoMock.save({Student student ->
            student.name == name
            student.family == family
            student.nationalCode == nationalCode
            student.field == field
        })
    }

    def "should get a list of students returned by the repository"() {
        given:
        def name = "name"
        def family = "family"
        def field = Major.MATHS
        def nationalCode= "123456"
        and:
        def student = Student.builder()
                .name(name)
                .family(family)
                .field(field)
                .nationalCode(nationalCode)
                .build()

        studentRepoMock.findAll() >> [student]

        when:
        def result = studentService.getAll()

        then:
        result.size() == 1
        result.any {
            it.field == field
            it.name == name
            it.family == family
            it.nationalCode == nationalCode
        }

    }

    def "should return the student, whose national code matches the parameter" () {
        given:
        def name = "name"
        def family = "family"
        def field = Major.MATHS
        def nationalCode= "123456"
        and:
        def student = Student.builder()
                .name(name)
                .family(family)
                .field(field)
                .nationalCode(nationalCode)
                .build()

        studentRepoMock.findByNationalCode(nationalCode) >> Optional.of(student)

        when:
        def resultOpt = studentService.getByNationalCode(nationalCode)

        then:
        resultOpt.isPresent()
        resultOpt.get().any {
            it.name == name
            it.family == family
            it.field == field
            it.nationalCode == nationalCode

        }
    }

}
