package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

public interface UserInterfaceService {
    public Student getRegistrationStudent();

    public String askQuestion(Question question, int numberQuestion);
}
