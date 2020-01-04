package ru.krasilova.otus.spring.homework5.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import ru.krasilova.otus.spring.homework5.models.Author;
import ru.krasilova.otus.spring.homework5.models.Book;
import ru.krasilova.otus.spring.homework5.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repository для работы с книгами должно")
@JdbcTest
@Import({BookReposiryJdbc.class, GenreRepositoryJdbc.class,AuthorRepositoryJdbc.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookReposiryJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 2;
    private static final int NEW_BOOK_ID = 3;
    private static final String NEW_BOOK_NAME = "Новая книга";
    private static final String BOOK_NAME = "Загадочное происшествие в Стайлзе";
    private static final int DEFAULT_BOOK_ID = 1;


    @Autowired
    private BookReposiryJdbc jdbcBook;

    @Autowired
    private GenreRepositoryJdbc jdbcGenre;
    @Autowired
    private AuthorRepositoryJdbc jdbcAuthor;





    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author author = jdbcAuthor.getById(1);
        Genre genre = jdbcGenre.getById(1);
        Book book = new Book(NEW_BOOK_ID, NEW_BOOK_NAME, author, genre);
        Long id = 1l;
        Long newId = jdbcBook.insert(NEW_BOOK_NAME, id, id);
        Book actual = jdbcBook.getById(newId);
        assertThat(actual.getName()).isEqualTo(book.getName());
        assertThat(actual.getGenre().getName()).isEqualTo(book.getGenre().getName());
        assertThat(actual.getAuthor().getFirstName()).isEqualTo(book.getAuthor().getFirstName());
        assertThat(actual.getAuthor().getSecondName()).isEqualTo(book.getAuthor().getSecondName());
        assertThat(actual.getAuthor().getLastName()).isEqualTo(book.getAuthor().getLastName());
        assertThat(actual.getAuthor().getBirthDate()).isEqualTo(book.getAuthor().getBirthDate());
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookId() {
        Book book = jdbcBook.getById(DEFAULT_BOOK_ID);
        assertThat(book.getName()).isEqualTo(BOOK_NAME);
    }

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedBooksCount() {
        assertThat(jdbcBook.count()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

}