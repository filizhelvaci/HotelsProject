package com.flz.config;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig {

    static PostgreSQLContainer<?> userDbContainer = new PostgreSQLContainer<>(DockerImageName
            .parse("postgres:15"))
            .withDatabaseName("hotel_u")
            .withUsername("postgres")
            .withPassword("654321");

    @BeforeAll
    static void beforeAll() {
        userDbContainer.start();
    }

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", userDbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", userDbContainer::getUsername);
        registry.add("spring.datasource.password", userDbContainer::getPassword);
    }
}
