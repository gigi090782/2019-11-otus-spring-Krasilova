package ru.krasilova.otus.spring.homework8.repositories;

import ru.krasilova.otus.spring.homework8.models.Book;

import java.util.List;

public interface AuthorCustomRepository {
    List<Book> getAuthorsBooksById(String Id);
    long getBooksElementsLengthByAuthorId(String id);

}
