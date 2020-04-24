package ru.krasilova.otus.spring.homework18.services;

import ru.krasilova.otus.spring.homework18.models.Author;
import ru.krasilova.otus.spring.homework18.models.Book;
import ru.krasilova.otus.spring.homework18.models.Comment;
import ru.krasilova.otus.spring.homework18.models.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<Author> getAllAuthors();

    Optional<Author> findAuthorById(Long id);

    List<Author> findAuthorByLastName(String lastName);

    List<Book> getAllBooks();

    List<Book> findAllBooksByAuthorId(Long id);

    List<Book> findAllBooksByAuthorLastName(String lastName);

    List<Book> findAllBooksByGenreName(String genreName);

    boolean existsBookByAuthorId(long authorId);

    boolean existsBookByGenreId(long genreId);

    List<Comment> findCommentByBookId(Long id);

    List<Comment> getAllComments();

    void deleteAllCommentsByBookId(long bookId);

    List<Genre> getAllGenres();

    Genre findGenreByName(String name);

    Author saveAuthor(Author author);

    void deleteAuthorById(Long id);

    Optional<Book> findBookById(Long id);

    Book saveBook(Book book);

    void deleteBookById(Long id);


    Optional<Comment> findCommentById(Long id);

    Comment saveComment(Comment comment);

    void deleteCommentById(Long id);

    Optional<Genre> findGenreById(Long id);

    Genre saveGenre(Genre genre);

    void deleteGenreById(Long id);
}
