package com.example.task_app.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql

@DataJpaTest(properties = ["spring.jpa.properties.javax.persistence.validation.mode=none"])
class TaskRepositoryEmbeddedTest {

    @Autowired
    private lateinit var objectUnderTest: TaskRepository


    private val numberOfRecordsInTestDataSql = 3
    private val numberOfOpenRecordsInTestDataSql = 1
    private val numberOfClosedRecordsInTestDataSql = 2


    @Test
    @Sql

}