package ru.krasilova.otus.spring.homework6.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.krasilova.otus.spring.homework6.models.Author;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами должно")
@DataJpaTest
@Import(AuthorRepositoryImpl.class)


class AuthorRepositoryImplTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String AUTHOR_FIRSTNAME = "Имя";
    private static final String AUTHOR_SECONDTNAME = "Отчество";
    private static final String AUTHOR_LASTTNAME = "Фамилия";


    @Autowired
    private AuthorRepositoryImpl repositoryAuthor;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужном авторе по его id")
    @Test
    void shouldFindExpectedAuthorById() {
        Author ActualAuthor = repositoryAuthor.findById(FIRST_AUTHOR_ID);
        Author expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(ActualAuthor).isEqualToComparingFieldByField (expectedAuthor);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val authors = repositoryAuthor.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS).allMatch(s -> !s.getFirstName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }



    @DisplayName(" должен корректно сохранять всю информацию об авторе")
    @Test
    void shouldSaveAllAuthorInfo() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date birthdate = format.parse("01.01.1990");
        Author author = new Author(0, AUTHOR_FIRSTNAME,AUTHOR_SECONDTNAME, AUTHOR_LASTTNAME, birthdate);
        repositoryAuthor.save(author);
        assertThat(author.getId()).isGreaterThan(0);

        Author actualAuthor = em.find(Author.class, author.getId());
        assertThat(actualAuthor).isNotNull().matches(s -> s.getFirstName().equals(AUTHOR_FIRSTNAME));
        assertThat(actualAuthor).isNotNull().matches(s -> s.getSecondName().equals(AUTHOR_SECONDTNAME));
        assertThat(actualAuthor).isNotNull().matches(s -> s.getLastName().equals(AUTHOR_LASTTNAME));
        assertThat(actualAuthor).isNotNull().matches(s -> s.getBirthDate().equals(birthdate));
    }





}

