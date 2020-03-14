package ru.krasilova.otus.spring.homework13.exceptions;

public class GenreNotFoundException extends Exception {
    public GenreNotFoundException(String message) {
        super(message);
    }

    public GenreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
