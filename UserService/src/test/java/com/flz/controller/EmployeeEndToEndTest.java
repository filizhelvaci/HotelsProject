package com.flz.controller;

import com.flz.cleaner.EmployeeTestCleaner;
import com.flz.port.EmployeeReadPort;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeEndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeReadPort employeeReadPort;

    @Autowired
    private EmployeeTestCleaner testCleaner;

    @BeforeEach
    void cleanBeforeTest() {

        testCleaner.cleanEmployees();
    }

    private static final String BASE_PATH = "/api/v1";


}
