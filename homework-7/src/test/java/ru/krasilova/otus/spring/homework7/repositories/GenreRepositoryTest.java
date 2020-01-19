package ru.krasilova.otus.spring.homework7.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.krasilova.otus.spring.homework7.models.Genre;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с жанрами должно")
@DataJpaTest
@Transactional
class GenreRepositoryTest {

    private static final String NEW_GENRE_NAME = "Фентези";

    @Autowired
    private GenreRepository repository;
    @Autowired
    private TestEntityManager em;



    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreId() {
        Genre genre = new Genre(0, NEW_GENRE_NAME,null);
        em.persist(genre);
        Genre genreForCompare = this.repository.findByName(NEW_GENRE_NAME);
        assertThat(genreForCompare.getName()).isEqualTo(NEW_GENRE_NAME);

    }


}