package com.flz;

import com.flz.cleaner.DepartmentTestCleaner;
import com.flz.cleaner.EmployeeTestCleaner;
import com.flz.cleaner.PositionTestCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseEndToEndTest {

    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                    .withDatabaseName("hotel_u")
                    .withUsername("postgres")
                    .withPassword("654321");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected DepartmentTestCleaner departmentTestCleaner;

    @Autowired
    protected PositionTestCleaner positionTestCleaner;

    @Autowired
    protected EmployeeTestCleaner employeeTestCleaner;

    @BeforeEach
    void clean() {
        departmentTestCleaner.cleanTestDepartments();
        positionTestCleaner.cleanTestPositions();
        employeeTestCleaner.cleanTestEmployees();
    }

}
