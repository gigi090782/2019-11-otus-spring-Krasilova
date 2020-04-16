package ru.krasilova.otus.spring.service;

import com.fasterxml.jackson.core.JsonToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.configuration.ConfigAnswers;
import ru.krasilova.otus.spring.dao.QuestionDao;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.domain.Student;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuestionDao questionDao;
    private final UserInterfaceService userInterfaceService;
    private final MessageService messageService;
    private final ConfigAnswers configAnswers;

    @Autowired
    public QuizServiceImpl(QuestionDao questionDao,
                           UserInterfaceService userInterfaceService,
                           MessageService messageService,
                           ConfigAnswers configAnswers) {

        this.userInterfaceService = userInterfaceService;
        this.messageService = messageService;
        this.questionDao = questionDao;
        this.configAnswers = configAnswers;
    }


    private void registerStudent(Quiz quiz) {
        Student student = new Student();


        student = userInterfaceService.getRegistrationStudent(messageService.getMessage("question.firstname"),
                messageService.getMessage("question.lastname"));
        quiz.setStudent(student);
    }


    @SneakyThrows
    @Override
    public void setQuestions(Quiz quiz) {

        quiz.setQuestions(questionDao.getQuestions());
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
        if (quiz.getCorrectAnswersCount() >= configAnswers.getCountToOk()) {
            result = messageService.getMessage("result.ok");
        } else {
            result = messageService.getMessage("result.failed");
        }
        ;
        Object[] formatResult = new Object[4];
        formatResult[0] = quiz.getStudent().getLastName().toUpperCase() + " " + quiz.getStudent().getFirstName().toUpperCase();
        formatResult[1] = quiz.getCorrectAnswersCount();
        formatResult[2] = quiz.getWrongAnswersCount();
        formatResult[3] = result;
        userInterfaceService.showResult(messageService.getMessageFormat("result.show", formatResult));

    }
}

