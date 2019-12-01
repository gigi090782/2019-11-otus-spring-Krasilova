package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Quiz;

public interface QuizService {
    public Quiz createQuiz();
    public void runQuiz(Quiz quiz);
    public void showResult(Quiz quiz);
}
