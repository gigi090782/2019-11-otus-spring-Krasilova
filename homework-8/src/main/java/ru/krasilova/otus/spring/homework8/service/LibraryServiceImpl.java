
package ru.krasilova.otus.spring.homework8.service;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework8.exceptions.AuthorHasBooksException;
import ru.krasilova.otus.spring.homework8.exceptions.AuthorNotFoundException;
import ru.krasilova.otus.spring.homework8.exceptions.BookNotFoundException;
import ru.krasilova.otus.spring.homework8.exceptions.GenreNotFoundException;
import ru.krasilova.otus.spring.homework8.models.*;
import ru.krasilova.otus.spring.homework8.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework8.repositories.BookRepository;
import ru.krasilova.otus.spring.homework8.repositories.CommentRepository;
import ru.krasilova.otus.spring.homework8.repositories.GenreRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryServiceImpl implements LibraryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;
    private final IOService ioService;

    @Override
    public void addNewGenre(String genreName) {
        Genre genre = new Genre(genreName);
        genreRepository.save(genre);
    }

    @Override
    public void addNewAuthor(String firstName, String secondName, String lastName, Date birthdate) {
        Author author = new Author(firstName, secondName, lastName, birthdate);
        authorRepository.save(author);
    }

    @Override

    public boolean addNewBook(String bookName, String authorId, String genreId, int isDefaultAdd) throws Exception {
        Author author;
        Genre genre;
        if (isDefaultAdd == 1) {
            val authors = authorRepository.findAll();
            if (authors.size() == 0) {
                throw new AuthorNotFoundException("Авторы не заведены в библиотеке!");
            }
            author = authors.get(0);
            val genres = genreRepository.findAll();
            if (genres.size() == 0) {
                throw new AuthorNotFoundException("Жанры не заведены в библиотеке!");
            }
            genre = genres.get(0);
        } else {

            Optional<Author> optionalAuthor = authorRepository.findById(authorId);
            author = optionalAuthor.orElseThrow(() -> new AuthorNotFoundException("Данный автор не заведен в библиотеке!"));
            Optional<Genre> optionalGenre = genreRepository.findById(genreId);
            genre = optionalGenre.orElseThrow(() -> new GenreNotFoundException("Данный жанр не заведен в библиотеке!"));
        }
        Book book = new Book(bookName, author, genre);
        bookRepository.save(book);
        return true;
    }

    @Override
    public String addNewComment(String text, String bookId, int isDefaultAdd) throws Exception {
        Book book;
        if (isDefaultAdd == 1) {
            val books = bookRepository.findAll();
            if (books.size() == 0) {
                throw new BookNotFoundException("Книги не заведены в библиотеке!");
            }
            book = books.get(0);
            bookId = book.getId();
        } else {
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            book = optionalBook.orElseThrow(() -> new BookNotFoundException("Данная книга не найдена в библиотеке!"));
        }
        Comment comment = new Comment(text, book);
        commentRepository.save(comment);
        return bookId;
    }

    @Override
    public boolean deleteComment(String commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment;
        if (optionalComment.isEmpty()) {
            ioService.printString("Данный комментарий не найден!");
            return false;
        } else {
            comment = optionalComment.get();
        }

        commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public boolean deleteAuthor(String authorId, int isDeleteFirst) throws Exception {
        Author author;
        if (isDeleteFirst == 1) {
            val authors = authorRepository.findAll();
            if (authors.size() == 0) {
                throw new AuthorNotFoundException("Авторы не заведены в библиотеке!");
            }
            author = authors.get(0);
            authorId = author.getId();
        } else {
            Optional<Author> optionalAuthor = authorRepository.findById(authorId);
            author = optionalAuthor.orElseThrow(() -> new AuthorNotFoundException("Данный автор не заведен в библиотеке!"));
        }
        if (!authorRepository.canRemoveAuthor(authorId)) {
            throw new AuthorHasBooksException("У автора имеются книги! Удаление невозможно!");
        }
        authorRepository.delete(author);
        return true;
    }

    @Override
    public boolean deleteGenre(String genreId, int isDeleteFirst) throws Exception {
        Genre genre;
        if (isDeleteFirst == 1) {
            val genres = genreRepository.findAll();
            if (genres.size() == 0) {
                throw new GenreNotFoundException("Жанры не заведены в библиотеке!");
            }
            genre = genres.get(0);
        } else {
            Optional<Genre> optionalGenre = genreRepository.findById(genreId);
            genre = optionalGenre.orElseThrow(() -> new AuthorNotFoundException("Данный жанр не заведен в библиотеке!"));
        }
        genreRepository.delete(genre);
        return true;
    }

    @Override
    public boolean deleteBook(String bookId, int isDeleteFirst) throws Exception {
        Book book;
        if (isDeleteFirst == 1) {
            val books = bookRepository.findAll();
            if (books.size() == 0) {
                throw new BookNotFoundException("Книги не заведены в библиотеке!");
            }
            book = books.get(0);
        } else {
            Optional<Book> optionalBook = bookRepository.findById(bookId);
            book = optionalBook.orElseThrow(() -> new BookNotFoundException("Данная книга не заведена в библиотеке!"));
        }
        bookRepository.delete(book);
        return true;
    }


    @Override
    public void showAllGenres() {
        String showGenre;
        List<Genre> listGenres = genreRepository.findAll();
        for (Genre genre : listGenres) {
            showGenre = "Id: = " + genre.getId() + " Name: " + genre.getName();
            ioService.printString(showGenre);
        }
    }


    @Override
    public void showAllAuthors() {
        String showAuthor;
        List<Author> listAuthor = authorRepository.findAll();
        for (Author author : listAuthor) {
            showAuthor = "Id: = " + author.getId() + " FirstName: " + author.getFirstName() +
                    " SecondName: " + author.getSecondName() + " LastName: " + author.getLastName() +
                    " Birthdate: " + author.getBirthDate();
            ioService.printString(showAuthor);
        }
    }

    @Override
    public void showAllBooks() {
        List<Book> listBook = bookRepository.findAll();
        if (listBook.size() != 0) {
            showBook(listBook);
        }
    }

    @Override
    public void showAllBooksByAuthorLastName(String lastName) {
        List<Book> listBook = bookRepository.findAllByAuthorLastName(lastName);
        if (listBook.size() != 0) {
            showBook(listBook);
        }

    }

    @Override
    public void showAllBooksByAuthorId(String id) {
        List<Book> listBook = bookRepository.findAllByAuthorId(id);
        showBook(listBook);

    }

    @Override
    public void showAllBooksByGenreName(String genreName) {
        List<Book> listBook = bookRepository.findAllByGenreName(genreName);
        if (listBook.size() != 0) {
            showBook(listBook);
        }
    }

    @Override
    public void showAllComments() {
        List<Comment> listComment = commentRepository.findAll();
        if (listComment.size() != 0) {
            showComments(listComment);
        }
    }

    @Override
    public void showAllCommentsByBookId(String id) {
        List<Comment> listComment = commentRepository.findByBookId(id);
        if (listComment.size() != 0) {
            showComments(listComment);
        }
    }

    public void showBook(List<Book> listBook) {
        String showBook;
        for (Book book : listBook) {
            showBook = "Id: = " + book.getId() + " BookName: " + book.getName() +
                    " Author: " + book.getAuthor().getFirstName() + " " +
                    book.getAuthor().getSecondName() + " " +
                    book.getAuthor().getLastName() + " " +
                    book.getAuthor().getBirthDate() +
                    " Genre: " + book.getGenre().getName();
            ioService.printString(showBook);
        }
    }

    public void showComments(List<Comment> listComment) {
        String showComment;
        for (Comment comment : listComment) {
            showComment = " Comment: Id = " + comment.getId() + " Comment = " + comment.getText();
            ioService.printString(showComment);


        }
    }
}
