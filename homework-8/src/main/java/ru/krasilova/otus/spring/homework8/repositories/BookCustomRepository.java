package ru.krasilova.otus.spring.homework8.repositories;

import ru.krasilova.otus.spring.homework8.models.Book;

import java.util.List;

public interface BookCustomRepository {

    void removeBooksByAuthorId(String id);
    void removeBooksByGenreId(String id);
    void removeCommentsByBooksId(String id);
    boolean existsByAuthorId(String authorId);
}
