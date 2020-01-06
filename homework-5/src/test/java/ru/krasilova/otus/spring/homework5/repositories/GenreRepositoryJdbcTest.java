package ru.krasilova.otus.spring.homework5.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.krasilova.otus.spring.homework5.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с жанрами должно")
@JdbcTest
@Import(GenreRepositoryJdbc.class)

class GenreRepositoryJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 3;
    private static final int NEW_GENRE_ID = 4;
    private static final String NEW_GENRE_NAME = "Фентези";
    private static final String GENRE_NAME = "Фантастика";
    private static final int DEFAULT_GENRE_ID = 1;


    @Autowired
    private GenreRepositoryJdbc jdbc;

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {

        Genre genre = new Genre(NEW_GENRE_ID, NEW_GENRE_NAME);
        Long newId = jdbc.insert(NEW_GENRE_NAME);
        Genre actual = jdbc.getById(newId);
        assertThat(actual).isEqualToComparingFieldByField(genre);
    }


    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreId() {
        Genre genre = jdbc.getById(DEFAULT_GENRE_ID);
        assertThat(genre.getName()).isEqualTo(GENRE_NAME);
    }

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedGenresCount() {
        assertThat(jdbc.count()).isEqualTo(EXPECTED_GENRES_COUNT);
    }
}