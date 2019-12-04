package ru.krasilova.otus.spring.service;

import ru.krasilova.otus.spring.dao.QuestionDao;
import ru.krasilova.otus.spring.domain.Question;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.domain.Student;



public class QuizServiceImpl  implements QuizService {

    private final QuestionDao questionDao;
    private UserInterfaceService userInterfaceService;


    public QuizServiceImpl (QuestionDao questionDao, UserInterfaceService userInterfaceService)
    {
        this.questionDao = questionDao;
        this.userInterfaceService = userInterfaceService;
    }


    private void registerStudent(Quiz quiz)
    {
        Student student = new Student();
        student = userInterfaceService.getRegistrationStudent();
        quiz.setStudent(student);
    };

    public void setQuestions (Quiz quiz)
    {
      quiz.setQuestions(questionDao.getQuestions());
    }

    @Override
    public Quiz createQuiz() {
        Quiz quiz = new Quiz();
        registerStudent(quiz);
        setQuestions(quiz);
        return quiz;
    }

    @Override
    public void runQuiz(Quiz quiz) {

        int questionNumber = 1;
        String answerStudent;

        for(Question question: quiz.getQuestions())
        {
            answerStudent = userInterfaceService.askQuestion(question, questionNumber);
            if (answerStudent.equals(question.getTextAnswer())) {
                quiz.increaseCorrectAnswersCount();
            }
            else {
                quiz.increaseWrongAnswersCount();
            }

        }


        System.out.println(quiz.getQuestions().size());

    }

    @Override
    public void showResult(Quiz quiz) {
        System.out.println("Результаты тестирования:");
        System.out.format("Фамилия и имя студента: %s %s \n",  quiz.getStudent().getLastName(),quiz.getStudent().getFirstName());
        System.out.format("Правильных ответов - %d, неправильных ответов - %d", quiz.getCorrectAnswersCount() , quiz.getWrongAnswersCount());
    }
}

