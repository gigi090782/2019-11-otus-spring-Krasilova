package ru.krasilova.otus.spring.homework13.exceptions;

public class AuthorNotFoundException extends Exception {
    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

