package com.student.sample.controller

import com.student.sample.common.integration.IntegrationSpec
import com.student.sample.dto.StudentDto
import com.student.sample.model.Major
import spock.lang.Unroll

class StudentControllerInt extends IntegrationSpec {


    @Unroll
    def"should respond properly to the registration request"() {
        given:
        def studentDto = StudentDto.builder()
                .name(nameParam)
                .family(familyParam)
                .field(fieldParam)
                .nationalCode(codeParam)
                .build()

        when:
        def response = restTemplate.postForEntity("/student/register", studentDto, String.class)

        then:
        response.body.split(",").find {
            expectedMessage.contains(it)
        }

        where:
        nameParam | familyParam | fieldParam  | codeParam      || expectedMessage
        ""        | "family"    | Major.MATHS | "12345"        || "[name:must not be blank]"
        "name"    | "family"    | Major.MATHS | "12345678910"  || "[nationalCode:size must be between 5 and 10]"
        null      | "family"    | Major.MATHS | "123"          || "[name:must not be blank],[nationalCode:size must be between 5 and 10]"

    }

}
