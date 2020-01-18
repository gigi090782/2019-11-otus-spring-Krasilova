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
import ru.krasilova.otus.spring.homework7.models.Book;
import ru.krasilova.otus.spring.homework7.models.Genre;

import javax.transaction.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с книгами должно")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class BookRepositoryTest {


    private static final String NEW_BOOK_NAME = "Новая книга";
    private static final String BOOK_NAME = "Загадочное происшествие в Стайлзе";
    private static final String NEW_AUTHOR_FIRSTNAME = "ИМЯ";
    private static final String NEW_AUTHOR_SECONDNAME = "ОТЧЕСТВО";
    private static final String NEW_AUTHOR_LASTNAME = "ФАМИЛИЯ";
    private static final String NEW_GENRE_NAME = "Фентези";
    private static final String NEW_GENRE_DETECTIVE = "Детектив";


    @Autowired
    private BookRepository repository;
    @Autowired
    private TestEntityManager em;



    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() throws ParseException {

        Genre genre = new Genre(0, NEW_GENRE_NAME,null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatter.parse("1999-01-01");
        date.setTime(0);
        Author author = new Author(0, NEW_AUTHOR_FIRSTNAME,NEW_AUTHOR_SECONDNAME, NEW_AUTHOR_LASTNAME, date,null);
        Book book = new Book(0, NEW_BOOK_NAME, author, genre);
        em.persist(book);
        Book actual = repository.findById(1);
        assertThat(actual.getName()).isEqualTo(book.getName());
        assertThat(actual.getGenre().getName()).isEqualTo(book.getGenre().getName());
        assertThat(actual.getAuthor().getFirstName()).isEqualTo(book.getAuthor().getFirstName());
        assertThat(actual.getAuthor().getSecondName()).isEqualTo(book.getAuthor().getSecondName());
        assertThat(actual.getAuthor().getLastName()).isEqualTo(book.getAuthor().getLastName());
        assertThat(actual.getAuthor().getBirthDate()).isEqualTo(book.getAuthor().getBirthDate());

    }

    @DisplayName("возвращать книги по жанру")
    @Test
    void shouldReturnExpectedBookByGenre()  throws ParseException{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = formatter.parse("1999-01-01");
        date.setTime(0);
        Author author = new Author(0, NEW_AUTHOR_FIRSTNAME,NEW_AUTHOR_SECONDNAME, NEW_AUTHOR_LASTNAME, date,null);
        Genre genre = new Genre(0, NEW_GENRE_NAME,null);
        Book book = new Book(0, NEW_BOOK_NAME, author, genre);
        em.persist(book);
        genre = new Genre(0, NEW_GENRE_DETECTIVE,null);
        book = new Book(0, BOOK_NAME, author, genre);
        em.persist(book);
        List<Book> actualListBook = repository.findAllByGenreName(NEW_GENRE_DETECTIVE);
        actualListBook.forEach(a ->assertThat(a.getGenre().getName()).isEqualTo(NEW_GENRE_DETECTIVE));
    }



}