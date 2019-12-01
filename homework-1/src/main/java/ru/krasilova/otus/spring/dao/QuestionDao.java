package ru.krasilova.otus.spring.dao;

import ru.krasilova.otus.spring.domain.Question;

import java.util.ArrayList;

public interface QuestionDao {
    public ArrayList<Question> getQuestions();
}
