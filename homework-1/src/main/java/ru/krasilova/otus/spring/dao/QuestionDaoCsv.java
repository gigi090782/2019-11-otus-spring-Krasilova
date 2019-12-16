package ru.krasilova.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.configuration.Config;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.exceptions.NotCorrectFileWithQuestionsException;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository("questionDao")
public class QuestionDaoCsv implements QuestionDao {

    private final  Config config;
    public QuestionDaoCsv(Config config) {
        this.config = config;

    }

    @Override
    public List<Question> getQuestions() throws Exception {
        String filePath = String.format(config.getFilenamePattern(), config.getLocale().toString());
        try {
            List<Question> questions = new ArrayList<>();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {

                String[] line = scanner.nextLine().split(";");
                questions.add(new Question(line[0], line[1]));

            }
            return questions;
        } catch (Exception e) {
            throw new NotCorrectFileWithQuestionsException("Не удалось прочитать вопросы", e);
        }


    }
}
