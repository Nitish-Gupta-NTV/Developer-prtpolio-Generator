package com.example.developer;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Test
    void testsignup() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "ggfffgg@vbvb.vbvtest");
        map.put("password", "Password@123");
        ResponseEntity<String>responce=testRestTemplate.postForEntity("/api/auth/register",map,String.class);
     //assertEqulas(HttpStatus.OK responce.getStatusCode());
       // assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK,responce.getStatusCode());
    }


}
