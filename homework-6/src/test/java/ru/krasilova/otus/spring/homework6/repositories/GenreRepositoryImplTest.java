package ru.krasilova.otus.spring.homework6.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.krasilova.otus.spring.homework6.models.Genre;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с жанрами должно")
@DataJpaTest
@Import(GenreRepositoryImpl.class)


class GenreRepositoryImplTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;
    private static final long FIRST_GENRE_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String GENRE_NAME = "Фантастика";
    private static final String NEW_GENRE_NAME = "Новый жанр";


    @Autowired
    private GenreRepositoryImpl repositoryGenre;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном жанре по его id")
    @Test
    void shouldFindExpectedGenreById() {
        val ActualGenre = repositoryGenre.findById(FIRST_GENRE_ID);
        val expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(ActualGenre).isEqualToComparingFieldByField (expectedGenre);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectGenreList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val genres = repositoryGenre.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES).allMatch(s -> !s.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }



    @DisplayName(" должен корректно сохранять всю информацию об авторе")
    @Test
    void shouldSaveAllAuthorInfo()  {

        Genre genre = new Genre(0, NEW_GENRE_NAME, null);
        repositoryGenre.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);

        Genre actualGenre = em.find(Genre.class, genre.getId());
        assertThat(actualGenre).isNotNull().matches(s -> s.getName().equals(NEW_GENRE_NAME));

    }





}

