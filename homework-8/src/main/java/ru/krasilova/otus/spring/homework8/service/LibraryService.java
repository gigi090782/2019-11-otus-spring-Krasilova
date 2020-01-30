package ru.krasilova.otus.spring.homework8.service;

import ru.krasilova.otus.spring.homework8.exceptions.BookNotFoundException;
import ru.krasilova.otus.spring.homework8.exceptions.GenreNotFoundException;

import java.util.Date;

public interface LibraryService {
    void addNewGenre(String genreName);

    void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate);

    boolean addNewBook(String bookName, String authorId, String genreId, int isDefaultAdd) throws Exception;

    String addNewComment(String text, String bookId, int isDefaultAdd) throws Exception;

    boolean deleteComment(String commentId);

    boolean deleteAuthor(String authorId, int isDeleteFirst) throws Exception;

    boolean deleteGenre(String genreId, int isDeleteFirst) throws Exception;

    boolean deleteBook(String bookId, int isDeleteFirst) throws Exception;

    void showAllGenres();

    void showAllAuthors();

    void showAllBooks();

    void showAllBooksByAuthorLastName(String lastName);

    void showAllBooksByAuthorId(String id);

    void showAllBooksByGenreName(String genreName);

    void showAllComments();

    void showAllCommentsByBookId(String id);


}
