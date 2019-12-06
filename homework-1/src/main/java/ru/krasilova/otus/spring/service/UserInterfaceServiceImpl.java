package ru.krasilova.otus.spring.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

import java.util.Scanner;

@Configuration
@Service
public class UserInterfaceServiceImpl implements UserInterfaceService {
    private final static Scanner scanner = new Scanner(System.in);

    @Override
    public Student getRegistrationStudent(String questionFirstName, String questionLastName) {
        Student student = new Student();
        System.out.println(questionLastName);
        student.setLastName(scanner.nextLine());
        System.out.println(questionFirstName);
        student.setFirstName(scanner.nextLine());
        return student;
    }

    @Override
    public String askQuestion(Question question, int numberQuestion) {
        String answerStudent;
        System.out.printf(question.getTextQuestion());
        answerStudent = scanner.nextLine();
        return answerStudent;
    }
}
