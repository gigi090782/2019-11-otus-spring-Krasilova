package ru.krasilova.otus.spring.homework12;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)

@DisplayName("Тест BookController")
@ContextConfiguration(classes = LibraryApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {


    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Должен возвращать HttpStatus.OK авторизованному пользователю ")
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("admin", "admin1")
                .getForEntity("/", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void foo_test() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.withBasicAuth(
                "user", "passwd").getForEntity("/", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
