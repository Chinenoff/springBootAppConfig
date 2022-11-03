//package com.example.springbootapp1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    private static TestRestTemplate restTemplate;

    private static GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    private static GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        Integer devAppMappedPort = devApp.getMappedPort(8080);
        Integer prodAppMappedPort = prodApp.getMappedPort(8081);

        ResponseEntity<Integer> entityFromDev = restTemplate.getForEntity("http://localhost:" + devAppMappedPort + "/profile", Integer.class);
        ResponseEntity<Integer> entityFromProd = restTemplate.getForEntity("http://localhost:" + prodAppMappedPort + "/profile", Integer.class);

        Integer expectedDevAppMappedPort = 8080;
        Integer expectedProdAppMappedPort = 8081;

        Assertions.assertEquals(expectedDevAppMappedPort, entityFromDev);
        Assertions.assertEquals(expectedProdAppMappedPort, entityFromProd);
    }

}