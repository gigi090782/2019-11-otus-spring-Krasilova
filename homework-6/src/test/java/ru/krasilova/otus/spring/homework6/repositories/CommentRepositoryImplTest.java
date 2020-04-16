package ru.krasilova.otus.spring.homework6.repositories;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import ru.krasilova.otus.spring.homework6.models.Book;
import ru.krasilova.otus.spring.homework6.models.Comment;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Repository для работы с комментариями должно")
@DataJpaTest
@Import(CommentRepositoryImpl.class)


class CommentRepositoryImplTest {
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 3;
    private static final long FIRST_COMMENT_ID = 1L;
    private static final long FIRST_BOOK_ID = 1L;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final String COMMENT_NAME = "Отличный детектив";
    private static final String NEW_COMMENT_TEXT = "Новый комментарий";


    @Autowired
    private CommentRepositoryImpl repositoryComment;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен удалять заданного комментария по его id")
    @Test
    void shouldDeleteCommentById() {
        val firstComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        em.detach(firstComment);

        repositoryComment.deleteById(FIRST_COMMENT_ID);
        val deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }



    @DisplayName(" должен корректно сохранять комментарий")
    @Test
    void shouldSaveAllAuthorInfo()  {
        Book book =  em.find(Book.class, FIRST_BOOK_ID);
        Comment comment = new Comment(0, NEW_COMMENT_TEXT,book);
        repositoryComment.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        Comment actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull().matches(s -> s.getText().equals(NEW_COMMENT_TEXT));

    }

    @DisplayName("должен загружать список всех комментариев")
    @Test
    void shouldReturnCorrectCommentList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val comments = repositoryComment.getAll();
        assertThat(comments).isNotNull().hasSize(EXPECTED_NUMBER_OF_COMMENTS).allMatch(s -> !s.getText().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

}