package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

public interface UserInterfaceService {
    Student getRegistrationStudent(String questionFirstName, String questionLastName);

    String askQuestion(Question question, int numberQuestion);

    void showResult(String resultStr);
}
