package com.student.sample.common.integration

import com.student.sample.model.Major
import com.student.sample.model.Student
import com.student.sample.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class IntegrationSpec extends Specification{

    @Autowired
    StudentRepository repository

    @Autowired
    TestRestTemplate restTemplate

    def "cleanup"() {
        repository.deleteAll()
    }

}
