package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

public interface UserInterfaceService {
    public Student getRegistrationStudent(String questionFirstName, String questionLastName);

    public String askQuestion(Question question, int numberQuestion);

    public void showResult(String resultStr);
}
