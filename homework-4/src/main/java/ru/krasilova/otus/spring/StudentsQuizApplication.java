package ru.krasilova.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.krasilova.otus.spring.configuration.ConfigAnswers;
import ru.krasilova.otus.spring.configuration.ConfigLocale;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.service.QuizService;

@SpringBootApplication
public class StudentsQuizApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx =
                SpringApplication.run(StudentsQuizApplication.class, args);

    }

}
