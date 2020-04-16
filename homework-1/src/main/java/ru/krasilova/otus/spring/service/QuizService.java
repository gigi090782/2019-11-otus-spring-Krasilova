package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Quiz;

import javax.management.InvalidApplicationException;

public interface QuizService {
    Quiz createQuiz() throws Exception;

    void runQuiz(Quiz quiz);

    void showResult(Quiz quiz);
}
