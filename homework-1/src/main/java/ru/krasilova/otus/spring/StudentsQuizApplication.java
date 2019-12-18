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
        QuizService service = ctx.getBean(QuizService.class);
        Quiz quiz = service.createQuiz();
        service.runQuiz(quiz);
        service.showResult(quiz);

        /*
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);
        QuizService service = context.getBean(QuizService.class);
        Quiz quiz = service.createQuiz();
        service.runQuiz(quiz);
        service.showResult(quiz);
        */

        /*
        SpringApplication.run(StudentsQuizApplication.class, args);
        System.out.println((char)27 + "[30mЧЕРНЫЙ");
        System.out.println((char)27 + "[31mКРАСНЫЙ");
        System.out.println((char)27 + "[32mЗЕЛЕНЫЙ");
        System.out.println((char)27 + "[33mЖЕЛТЫЙ");
        System.out.println((char)27 + "[34mСИНИЙ");
        System.out.println((char)27 + "[35mПУРПУРНЫЙ");
        System.out.println((char)27 + "[36mБИРЮЗОВЫЙ");
        System.out.println((char)27 + "[37mБЕЛЫЙ");

        System.out.println((char)27 + "[40m");
        System.out.println((char)27 + "[41m");
        System.out.println((char)27 + "[42m");
        System.out.println((char)27 + "[43m");
        System.out.println((char)27 + "[44m");
        System.out.println((char)27 + "[45m");
        System.out.println((char)27 + "[46m");
        System.out.println((char)27 + "[47m");
        */
    }

}
