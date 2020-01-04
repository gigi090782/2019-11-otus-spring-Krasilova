package ru.krasilova.otus.spring.homework5.service;

import java.util.Date;

public interface LibraryService {
    void addNewGenre(String genreName);

    void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate);

    boolean addNewBook(String bookName, Long authorId, Long genreId);

    void showAllGenres();

    void showAllAuthors();

    void showAllBooks();

    void showAllBooksByAuthorLastName(String lastName);

    void showAllBooksByAuthorId(Long id);

    void showAllBooksByGenreName(String genreName);


}
