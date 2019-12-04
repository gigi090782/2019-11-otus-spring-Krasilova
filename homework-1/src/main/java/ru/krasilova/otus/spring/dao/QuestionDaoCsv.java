package ru.krasilova.otus.spring.dao;

import ru.krasilova.otus.spring.domain.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionDaoCsv implements  QuestionDao {

    @Override
    public List<Question> getQuestions() {
        //ArrayList<Question> questions = new ArrayList<Question>();
        List<Question> questions = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream ("questions.csv");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(";");
            questions.add(new Question(line[0],line[1]));
        }


        return questions;
    }
}
