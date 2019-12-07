package ru.krasilova.otus.spring.dao;

import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.exceptions.NotCorrectFileWithQuestionsException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionDaoCsv implements QuestionDao {

    @Override
    public List<Question> getQuestions(String fileName) throws Exception {
        List<Question> questions = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {

            String[] line = scanner.nextLine().split(";");
            if (line.length != 2)
                throw new NotCorrectFileWithQuestionsException("Некорректные данные");
            questions.add(new Question(line[0], line[1]));

        }


        return questions;
    }
}
