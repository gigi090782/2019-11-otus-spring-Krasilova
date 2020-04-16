package ru.krasilova.otus.spring.homework8.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mapping.MappingException;
import ru.krasilova.otus.spring.homework8.models.Author;
import ru.krasilova.otus.spring.homework8.models.Book;
import ru.krasilova.otus.spring.homework8.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.krasilova.otus.spring.homework8.config", "ru.krasilova.otus.spring.homework8.repositories"})
@DisplayName("bookRepository при отсутствии listener-ов в контексте ")
class BookRepositoryTest{

    @Autowired
    private  BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("при удалении автора не должен удалять книги автора")
    @Test
    void shouldLeaveAuthorsBooksWhenRemovingAuthor() {
        // Загрузка студента и его первого знания
        val authors = authorRepository.findByLastName("Кристи");
        val author = authors.get(0);
        if (author != null) {
            String authorId = author.getId();
            List<Book> listBooks = bookRepository.findAllByAuthorId(authorId);
            val expectedBooksArrayLength = 0;
            authorRepository.delete(author);
            // Загружаем книги автора заново
            List<Book> listActualBooks  =  bookRepository.findAllByAuthorId(authorId);
            val actualBooksArrayLength = listActualBooks.size();
            assertThat(actualBooksArrayLength).isNotEqualTo(expectedBooksArrayLength);

        } else
        {
            System.out.println("Авторы не найдены!");

        }

    }



        @DisplayName("должен кидать MappingException во время сохранения книги с отсутствующим в БД жанром")
        @Test
        void shouldThrowMappingExceptionWhenSaveStudentWithNewKnowledge(){
            val authors = authorRepository.findAll();
            val author = authors.get(0);
            val book = new Book("Тестовая книга", author, new Genre("Неизвестный"));
            assertThatThrownBy(() -> bookRepository.save(book)).isInstanceOf(MappingException.class);
        }

}
