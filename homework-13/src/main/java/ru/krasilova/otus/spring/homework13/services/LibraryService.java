package ru.krasilova.otus.spring.homework13.services;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.krasilova.otus.spring.homework13.models.Book;


public interface LibraryService {
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    void saveBook(@Param("book")Book book);

    Book addNewBook(Book book);

}
