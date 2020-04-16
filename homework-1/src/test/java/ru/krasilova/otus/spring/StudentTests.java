package ru.krasilova.otus.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.krasilova.otus.spring.domain.Student;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentTests {

@DisplayName("корректно создаётся конструктором")
@Test
void shouldHaveCorrectConstructor() {
    Student student = new Student("Имя", "Фамилия");
    assertAll("student",
            () -> assertEquals("Имя", student.getFirstName()),
            () -> assertEquals("Фамилия", student.getLastName()));
}

    @DisplayName("должен устанавливать Имя")
    @Test
    void setFirstName() {
        String  firstNameTest = "ИмяТест";
        Student student = new Student("Имя", "Фамилия");
        student.setFirstName(firstNameTest);
        assertEquals(firstNameTest, student.getFirstName());
    }

    @DisplayName("должен устанавливать Фамилию")
    @Test
    void setLastName() {
        String  lastNameTest = "ФамилияТест";
        Student student = new Student("Имя", "Фамилия");
        student.setLastName(lastNameTest);
        assertEquals(lastNameTest, student.getLastName());
    }
}
