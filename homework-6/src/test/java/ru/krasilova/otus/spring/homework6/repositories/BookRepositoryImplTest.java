package ru.krasilova.otus.spring.homework6.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.krasilova.otus.spring.homework6.models.Author;
import ru.krasilova.otus.spring.homework6.models.Book;
import ru.krasilova.otus.spring.homework6.models.Comment;
import ru.krasilova.otus.spring.homework6.models.Genre;


import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;

@DisplayName("Repository для работы с книгами должно")
@DataJpaTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, CommentRepositoryImpl.class, GenreRepositoryImpl.class})

class BookRepositoryImplTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final long FIRST_BOOK_ID = 1L;
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long FIRST_GENRE_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String NEW_BOOK_NAME = "Новая книга";
    private static final String FIRST_BOOK_NAME = "Загадочное происшествие в Стайлзе";
    private static final String NEW_COMMENT = "Замечательная книга";



    @Autowired
    private BookRepositoryImpl repositoryBook;
    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по ее id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = repositoryBook.findById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isEqualToComparingFieldByField(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);


        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        List<Book> books = repositoryBook.findAllWithAllInfo();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(s -> !s.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName(" должен корректно сохранять информацию о книге")
    @Test
    void shouldSaveAllBookInfo() {
        val author = em.find(Author.class, FIRST_AUTHOR_ID);
        val genre = em.find(Genre.class, FIRST_GENRE_ID);
        val newbook = new Book (0,NEW_BOOK_NAME,author, genre);
        em.persist(newbook);
        val actualBook = em.find(Book.class, newbook.getId());
        assertThat(actualBook).isNotNull().matches(s -> !s.getName().equals(""))
                .matches(s -> s.getAuthor() != null)
                .matches(s -> s.getGenre() != null);
    }
    @DisplayName(" должен загружать информацию о нужной книге по ее имени")
    @Test
    void shouldFindExpectedBookByName() {
        val firstBook= em.find(Book.class, FIRST_BOOK_ID);
        List<Book> books = repositoryBook.findByName(FIRST_BOOK_NAME);
        assertThat(books).contains(firstBook);
    }

}