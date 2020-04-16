package ru.krasilova.otus.spring.homework5.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.krasilova.otus.spring.homework5.models.Author;
import ru.krasilova.otus.spring.homework5.models.Book;
import ru.krasilova.otus.spring.homework5.models.Genre;
import ru.krasilova.otus.spring.homework5.service.IOService;
import ru.krasilova.otus.spring.homework5.service.LibraryService;

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
    public void showAllBooksByGenreName(@ShellOption(defaultValue = "Детектив") String genreName) {
        ioService.printString("Все книги библиотеки по теме " + genreName + ":");
        libraryService.showAllBooksByGenreName(genreName);
    }


    @SneakyThrows
    @ShellMethod(value = "show all books by authorid", key = {"baid", "booksbyauthorid"})
    public void showAllBooksByAuthorId(@ShellOption(defaultValue = "1") Long authorid) {
        ioService.printString("Все книги библиотеки по id автора " + authorid.toString() + ":");
        libraryService.showAllBooksByAuthorId(authorid);
    }


    @SneakyThrows
    @ShellMethod(value = "show all books by authors's lastname", key = {"baln", "booksbyauthorlastname"})
    public void showAllBooksByAuthorLastName(@ShellOption(defaultValue = "Кристи") String lastName) {
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
                        @ShellOption(defaultValue = "1") Long authorId,
                        @ShellOption(defaultValue = "1") Long genreId) {
        try {
            boolean result = libraryService.addNewBook(bookName, authorId, genreId);
            if (result)
                libraryService.showAllBooks();
        } catch (Exception e) {
            ioService.printString("Ошибка в добавлении книги: " + e.getMessage());
        }
    }


}
