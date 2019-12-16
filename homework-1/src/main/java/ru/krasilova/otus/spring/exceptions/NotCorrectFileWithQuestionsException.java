package ru.krasilova.otus.spring.exceptions;

public class NotCorrectFileWithQuestionsException extends Exception {
    public NotCorrectFileWithQuestionsException(String message) {
        super(message);
    }

    public NotCorrectFileWithQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
