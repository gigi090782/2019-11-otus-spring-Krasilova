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

    private final String filePath;

    public  QuestionDaoCsv(Config config)
    {
     String localeStr =  config.getLocale().toString();
     this.filePath = "questions"+localeStr+".csv";

    }

    @Override
    public List<Question> getQuestions() throws Exception {
        try {
            List<Question> questions = new ArrayList<>();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.filePath);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(";");
                questions.add(new Question(line[0], line[1]));
            }
            return questions;
        } catch (Exception e)
        {
            throw new NotCorrectFileWithQuestionsException("Не удалось прочитать вопросы", e);
        }

    }
}
