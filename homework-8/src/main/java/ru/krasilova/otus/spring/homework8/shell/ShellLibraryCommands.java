package ru.krasilova.otus.spring.homework8.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.krasilova.otus.spring.homework8.models.Author;
import ru.krasilova.otus.spring.homework8.models.Book;
import ru.krasilova.otus.spring.homework8.models.Genre;
import ru.krasilova.otus.spring.homework8.service.IOService;
import ru.krasilova.otus.spring.homework8.service.LibraryService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@ShellComponent
@RequiredArgsConstructor
public class ShellLibraryCommands {

    private final LibraryService libraryService;
    private final IOService ioService;
    private Book book;
    private Author author;
    private Genre genre;

    @SneakyThrows
    @ShellMethod(value = "show all genres", key = {"g", "genres"})
    public void showAllGenres() {
        ioService.printString("Все жанры библиотеки:");
        libraryService.showAllGenres();
    }



    @SneakyThrows
    @ShellMethod(value = "show all authors", key = {"a", "authors"})
    public void showAllAuthors() {
        ioService.printString("Все авторы библиотеки:");
        libraryService.showAllAuthors();
    }

    @SneakyThrows
    @ShellMethod(value = "show all books", key = {"b", "books"})
    public void showAllBooks() {
        ioService.printString("Все книги библиотеки:");
        libraryService.showAllBooks();
    }

    @SneakyThrows
    @ShellMethod(value = "show all books by genre", key = {"bg", "booksbygenre"})
    public void showAllBooksByGenreName(@ShellOption(defaultValue = "Новый жанр") String genreName) {
        ioService.printString("Все книги библиотеки по теме " + genreName + ":");
        libraryService.showAllBooksByGenreName(genreName);
    }


    @SneakyThrows
    @ShellMethod(value = "show all books by authorid", key = {"baid", "booksbyauthorid"})
    public void showAllBooksByAuthorId(@ShellOption(defaultValue = "1") String authorid) {
        ioService.printString("Все книги библиотеки по id автора " + authorid.toString() + ":");
        libraryService.showAllBooksByAuthorId(authorid);
    }


    @SneakyThrows
    @ShellMethod(value = "show all books by authors's lastname", key = {"baln", "booksbyauthorlastname"})
    public void showAllBooksByAuthorLastName(@ShellOption(defaultValue = "Лукьяненко") String lastName) {
        ioService.printString("Все книги библиотеки по фамилии автора " + lastName + ":");
        libraryService.showAllBooksByAuthorLastName(lastName);
    }

    @SneakyThrows
    @ShellMethod(value = "add new genre", key = {"ag", "addgenre"})
    public void addGenre(@ShellOption(defaultValue = "Новый жанр") String genreName) {
        libraryService.addNewGenre(genreName);
        libraryService.showAllGenres();
    }


    @SneakyThrows
    @ShellMethod(value = "add new author", key = {"aa", "addauthor"})
    public void addAuthor(@ShellOption(defaultValue = "Сергей") String firstName,
                          @ShellOption(defaultValue = "Васильевич") String secondName,
                          @ShellOption(defaultValue = "Лукьяненко") String lastName,
                          @ShellOption(defaultValue = "1968-04-11") String birthDate) {

        DateFormat formatter;
        try {
            Date date;
            formatter = new SimpleDateFormat("yyyy-mm-dd");
            date = formatter.parse(birthDate);
            libraryService.addNewAuthor(firstName, secondName, lastName, date);
            libraryService.showAllAuthors();
        } catch (Exception e) {
            ioService.printString("Ошибка в добавлении автора: " + e.getMessage());
        }
    }


    @SneakyThrows
    @ShellMethod(value = "add new book", key = {"ab", "addbook"})
    public void addBook(@ShellOption(defaultValue = "Новая книга") String bookName,
                        @ShellOption(defaultValue = "") String authorId,
                        @ShellOption(defaultValue = "") String genreId,
                        @ShellOption(defaultValue = "1") int isDefault
                        ) {
        try {
            boolean result = libraryService.addNewBook(bookName, authorId, genreId,isDefault);
            if (result)
                libraryService.showAllBooks();
        } catch (Exception e) {
            ioService.printString("Ошибка в добавлении книги: " + e.getMessage());
        }
    }


    @SneakyThrows
    @ShellMethod(value = "show all comments", key = {"c", "comments"})
    public void showAllComments() {
        ioService.printString("Все комментарии: ");
        libraryService.showAllComments();
    }


    @SneakyThrows
    @ShellMethod(value = "show all comments by book_id", key = {"cbid", "commentsbybookid"})
    public void showAllCommentsByBookId( @ShellOption(defaultValue = "1") String bookId) {
        ioService.printString("Все комментарии по Id книги "+bookId+": ");
        libraryService.showAllCommentsByBookId(bookId);
    }

    @SneakyThrows
    @ShellMethod(value = "add new comment", key = {"ac", "addcomment"})
    public void addComment(@ShellOption(defaultValue = "Новый комментарий") String commentName,
                           @ShellOption(defaultValue = "") String bookId,
                           @ShellOption(defaultValue = "1") int isDefault) {
        try {
            bookId = libraryService.addNewComment(commentName, bookId,isDefault);
            if (bookId != "")
                libraryService.showAllCommentsByBookId(bookId);
        } catch (Exception e) {
            ioService.printString("Ошибка в добавлении комментария: " + e.getMessage());
        }
    }


    @SneakyThrows
    @ShellMethod(value = "delete comment", key = {"dc", "deletecomment"})
    public void deleteComment( @ShellOption(defaultValue = "1") String commentId) {
        try {
            boolean result = libraryService.deleteComment(commentId);
            if (result)
                libraryService.showAllComments();
        } catch (Exception e) {
            ioService.printString("Ошибка при удалении комментария: " + e.getMessage());
        }
    }


    @SneakyThrows
    @ShellMethod(value = "delete author", key = {"da", "deleteauthor"})
    public void deleteAuthor( @ShellOption(defaultValue = "") String authorId,
                              @ShellOption(defaultValue = "1") int isDeleteFirst
                              ) {
        try {
            boolean result = libraryService.deleteAuthor(authorId,isDeleteFirst);
            if (result)
                libraryService.showAllAuthors();
        } catch (Exception e) {
            ioService.printString("Ошибка при удалении автора: " + e.getMessage());
        }
    }

    @SneakyThrows
    @ShellMethod(value = "delete genre", key = {"dg", "deletegenre"})
    public void deleteGenre( @ShellOption(defaultValue = "") String genreId,
                              @ShellOption(defaultValue = "1") int isDeleteFirst
    ) {
        try {
            boolean result = libraryService.deleteGenre(genreId,isDeleteFirst);
            if (result)
                libraryService.showAllGenres();
        } catch (Exception e) {
            ioService.printString("Ошибка при удалении жанра: " + e.getMessage());
        }
    }

}
