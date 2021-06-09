package com.bp.RUIAN;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;


/**
 * This class tests Http request
 */
@SpringBootTest
public class HttpRequestTest {

    @Test
    @DisplayName("Should return default message")
    public void greetings() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        Assertions.assertTrue(restTemplate.getForObject("http://localhost:8080/ruian/",
                String.class).contains("Hello, this is a RUIAN REST API!"));
    }
}
