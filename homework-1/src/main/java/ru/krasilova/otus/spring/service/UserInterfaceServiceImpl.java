package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Student;

import java.util.Scanner;

public class UserInterfaceServiceImpl implements UserInterfaceService {
    private static Scanner scanner = new Scanner(System.in);

    @Override
    public Student getRegistrationStudent() {
        Student student = new Student();
        System.out.println("Введите фамилию:");
        student.setLastName(scanner.nextLine());
        System.out.println("Введите имя:");
        student.setFirstName(scanner.nextLine());
        return student;
    }

    @Override
    public String askQuestion(Question question, int numberQuestion) {
        String answerStudent;
        System.out.printf("Вопрос №%d: %s ", numberQuestion, question.getTextQuestion());
        answerStudent = scanner.nextLine();
        return answerStudent;
    }
}
