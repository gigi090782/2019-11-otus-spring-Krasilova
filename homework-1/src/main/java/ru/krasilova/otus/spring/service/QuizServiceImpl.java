package ru.krasilova.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.config.Config;
import ru.krasilova.otus.spring.dao.QuestionDao;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.domain.Student;

@Configuration
@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionDao questionDao;
    private final UserInterfaceService userInterfaceService;
    private final Config config;

    @Autowired
    public QuizServiceImpl(QuestionDao questionDao, UserInterfaceService userInterfaceService, Config config) {
        this.questionDao = questionDao;
        this.userInterfaceService = userInterfaceService;
        this.config = config;
        this.config.setLocale();
    }


    private void registerStudent(Quiz quiz) {
        Student student = new Student();
        student = userInterfaceService.getRegistrationStudent(config.getMessageSource("question.firstname"),
                config.getMessageSource("question.lastname"));
        quiz.setStudent(student);
    }

    ;

    public void setQuestions(Quiz quiz) throws Exception {

        quiz.setQuestions(questionDao.getQuestions(config.getMessageSource("path.questions")));
    }

    @Override
    public Quiz createQuiz() throws Exception {
        Quiz quiz = new Quiz();
        registerStudent(quiz);
        setQuestions(quiz);
        return quiz;
    }

    @Override
    public void runQuiz(Quiz quiz) {

        int questionNumber = 0;
        String answerStudent;
        for (Question question : quiz.getQuestions()) {
            questionNumber++;
            answerStudent = userInterfaceService.askQuestion(question, questionNumber);
            if (answerStudent.equals(question.getTextAnswer())) {
                quiz.increaseCorrectAnswersCount();
            } else {
                quiz.increaseWrongAnswersCount();
            }
        }
    }

    @Override
    public void showResult(Quiz quiz) {
        String result;
        if (quiz.getCorrectAnswersCount() >= config.getCountCorrectAnswersToOk()) {
            result = config.getMessageSource("result.ok");
        } else {
            result = config.getMessageSource("result.failed");
        }
        ;
        System.out.format(config.getMessageSource("result.show"),
                quiz.getStudent().getLastName().toUpperCase() + " " + quiz.getStudent().getFirstName().toUpperCase(),
                quiz.getCorrectAnswersCount(),
                quiz.getWrongAnswersCount(),
                result);
    }
}

