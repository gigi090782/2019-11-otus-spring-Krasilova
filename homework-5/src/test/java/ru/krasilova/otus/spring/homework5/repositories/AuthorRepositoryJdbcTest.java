package ru.krasilova.otus.spring.homework5.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.krasilova.otus.spring.homework5.models.Author;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами должно")
@JdbcTest
@Import(AuthorRepositoryJdbc.class)


class AuthorRepositoryJdbcTest {
    private static final int EXPECTED_AUTHORS_COUNT = 2;
    private static final int NEW_AUTHOR_ID = 3;
    private static final String NEW_AUTHOR_FIRSTNAME = "ИМЯ";
    private static final String NEW_AUTHOR_SECONDNAME = "ОТЧЕСТВО";
    private static final String NEW_AUTHOR_LASTNAME = "ФАМИЛИЯ";
    private static final String OLD_AUTHOR_FIRSTNAME = "Агата";
    private static final String OLD_AUTHOR_SECONDNAME = "Мэри Кларисса";
    private static final String OLD_AUTHOR_LASTNAME = "Кристи";
    private static final int DEFAULT_AUTHOR_ID = 1;


    @Autowired
    private AuthorRepositoryJdbc jdbc;


    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatter.parse("1999-01-01");
        date.setTime(0);
        Author author = new Author(NEW_AUTHOR_ID, NEW_AUTHOR_FIRSTNAME,NEW_AUTHOR_SECONDNAME, NEW_AUTHOR_LASTNAME, date);
        Long newId = jdbc.insert(NEW_AUTHOR_FIRSTNAME,NEW_AUTHOR_SECONDNAME, NEW_AUTHOR_LASTNAME, date);
        Author actual = jdbc.getById(newId);
        assertThat(actual).isEqualToComparingFieldByField(author);
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorId() {
        Author actual = jdbc.getById(DEFAULT_AUTHOR_ID);
        assertThat(actual.getFirstName()).isEqualTo(OLD_AUTHOR_FIRSTNAME);
        assertThat(actual.getSecondName()).isEqualTo(OLD_AUTHOR_SECONDNAME);
        assertThat(actual.getLastName()).isEqualTo(OLD_AUTHOR_LASTNAME);
    }

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }
}

