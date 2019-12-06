package ru.krasilova.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.service.QuizService;

public class Main {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuizService service = context.getBean(QuizService.class);
        Quiz quiz = service.createQuiz();
        service.runQuiz(quiz);
        service.showResult(quiz);


    }
}
