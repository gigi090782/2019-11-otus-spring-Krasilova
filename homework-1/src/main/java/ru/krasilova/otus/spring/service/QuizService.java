package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Quiz;

import javax.management.InvalidApplicationException;

public interface QuizService {
    public Quiz createQuiz() throws Exception;

    public void runQuiz(Quiz quiz);

    public void showResult(Quiz quiz);
}
