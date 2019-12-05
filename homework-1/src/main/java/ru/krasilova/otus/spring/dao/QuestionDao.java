package ru.krasilova.otus.spring.dao;

import ru.krasilova.otus.spring.domain.Question;

import java.util.List;

public interface QuestionDao {
    public List<Question> getQuestions() throws Exception;
}
