package ru.krasilova.otus.spring.homework7.service;

import java.util.Date;

public interface LibraryService {
    void addNewGenre(String genreName);

    void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate);

    boolean addNewBook(String bookName, Long authorId, Long genreId) throws Exception;

    boolean addNewComment(String text, Long bookId);

    boolean deleteComment(Long commentId);

    void showAllGenres();

    void showAllAuthors();

    void showAllBooks();

    void showAllBooksByAuthorLastName(String lastName);

    void showAllBooksByAuthorId(Long id);

    void showAllBooksByGenreName(String genreName);

    void showAllComments();

    void showAllCommentsByBookId(Long id);


}
