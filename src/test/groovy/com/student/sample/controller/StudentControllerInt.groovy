package com.student.sample.controller

import com.student.sample.advice.ErrorMessage
import com.student.sample.common.integration.IntegrationSpec
import com.student.sample.dto.StudentDto
import com.student.sample.model.Major
import spock.lang.Unroll

class StudentControllerInt extends IntegrationSpec {


    @Unroll
    def"should return an appropriate error message for an incorrect request"() {
        given:
        def studentDto = StudentDto.builder()
                .name(nameParam)
                .family(familyParam)
                .field(fieldParam)
                .nationalCode(codeParam)
                .build()

        when:
        def response = restTemplate.postForEntity("/student/register", studentDto, ErrorMessage.class)

        then:
        response.body.message.split(",").find {
            expectedMessage.contains(it)
        }

        where:
        nameParam | familyParam | fieldParam  | codeParam      || expectedMessage
        ""        | "family"    | Major.MATHS | "12345"        || "[name:must not be blank]"
        "name"    | "family"    | Major.MATHS | "12345678910"  || "[nationalCode:size must be between 5 and 10]"
        null      | "family"    | Major.MATHS | "123"          || "[name:must not be blank],[nationalCode:size must be between 5 and 10]"

    }

}
