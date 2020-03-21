package ru.krasilova.otus.spring.homework14.exceptions;

public class AuthorHasBooksException extends Exception {
    public AuthorHasBooksException(String message) {

        super(message);
    }

    public AuthorHasBooksException(String message, Throwable cause) {
        super(message, cause);
    }

}


