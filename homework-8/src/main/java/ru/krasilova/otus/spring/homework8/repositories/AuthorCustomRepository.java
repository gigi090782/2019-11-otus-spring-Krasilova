package ru.krasilova.otus.spring.homework8.repositories;

import ru.krasilova.otus.spring.homework8.models.Book;

import java.util.List;

public interface AuthorCustomRepository {
    boolean canRemoveAuthor(String id) throws Exception;
}
