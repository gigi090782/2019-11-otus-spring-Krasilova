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
import ru.krasilova.otus.spring.homework7.models.Author;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с авторами должно")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AuthorRepositoryTest {
    private static final String NEW_AUTHOR_FIRSTNAME = "ИМЯ";
    private static final String NEW_AUTHOR_SECONDNAME = "ОТЧЕСТВО";
    private static final String NEW_AUTHOR_LASTNAME = "ФАМИЛИЯ";

    @Autowired
    private AuthorRepository repository;
    @Autowired
    private TestEntityManager em;


    @DisplayName("добавлять автора в БД")
    @Test
    void shouldFindAuthorByLastName() throws  Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatter.parse("1999-01-01");
        date.setTime(0);
        Author author = new Author(0, NEW_AUTHOR_FIRSTNAME,NEW_AUTHOR_SECONDNAME, NEW_AUTHOR_LASTNAME, date,null);
        em.persist(author);
        List<Author> authorList = this.repository.findByLastName(NEW_AUTHOR_LASTNAME);
        authorList.forEach(a ->assertThat(a.getLastName()).isEqualTo(NEW_AUTHOR_LASTNAME));

    }


}

