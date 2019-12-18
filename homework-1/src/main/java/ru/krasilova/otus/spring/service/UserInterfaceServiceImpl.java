package ru.krasilova.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

import java.util.Scanner;

@Service
public class UserInterfaceServiceImpl implements UserInterfaceService {
    private final IOService ioService;

    public UserInterfaceServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }


    @Override
    public Student getRegistrationStudent(String questionFirstName, String questionLastName) {
        Student student = new Student();
        student.setLastName(this.ioService.readString(questionLastName));
        student.setFirstName(this.ioService.readString(questionFirstName));

        return student;
    }

    @Override
    public String askQuestion(Question question, int numberQuestion) {
        String answerStudent;
        answerStudent = this.ioService.readString(question.getTextQuestion());
        return answerStudent;
    }

    @Override
    public void showResult(String resultStr) {

        this.ioService.printString(resultStr);
    }
}
