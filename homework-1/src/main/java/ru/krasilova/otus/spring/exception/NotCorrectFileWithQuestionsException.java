package ru.krasilova.otus.spring.exception;

public class NotCorrectFileWithQuestionsException extends Exception {
    public NotCorrectFileWithQuestionsException(String message) {
        super(message);
    }

    public NotCorrectFileWithQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}




