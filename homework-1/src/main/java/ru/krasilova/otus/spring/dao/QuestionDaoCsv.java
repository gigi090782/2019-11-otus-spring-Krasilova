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
    public ArrayList<Question> getQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();

        File fileQuestions = new File(getClass().getClassLoader().getResource("questions.csv").getFile());
        try {
            Scanner scanner = new Scanner(fileQuestions);
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(";");
                questions.add(new Question(line[0],line[1]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return questions;
    }
}
