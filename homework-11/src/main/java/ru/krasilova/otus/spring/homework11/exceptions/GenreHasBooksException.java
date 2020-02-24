package ru.krasilova.otus.spring.homework11.exceptions;

public class GenreHasBooksException extends Exception {
    public GenreHasBooksException(String message) {
        super(message);
    }

    public GenreHasBooksException(String message, Throwable cause) {
        super(message, cause);
    }

}


