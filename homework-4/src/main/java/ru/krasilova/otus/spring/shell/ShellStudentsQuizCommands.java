package ru.krasilova.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.krasilova.otus.spring.domain.Quiz;
import ru.krasilova.otus.spring.domain.Student;
import ru.krasilova.otus.spring.service.IOService;
import ru.krasilova.otus.spring.service.QuizService;

@ShellComponent
@RequiredArgsConstructor
public class ShellStudentsQuizCommands {

    private final QuizService quizService;
    private final IOService ioService;
    private Quiz quiz;
    private boolean isTestPassed = false;



    @SneakyThrows
    @ShellMethod(value = "register qiuz command", key = {"rqs", "register"})
    public String registerQuizCommand(@ShellOption(defaultValue = "Марина") String firstName, @ShellOption(defaultValue = "Красилова") String lastName) {
        quiz = new Quiz();
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        quiz.setStudent(student);
        String res = String.format("Начало опроса \nСтудент: %s %s\n", firstName, lastName);
        return res;

    }


    @ShellMethod(value = "run qiuz command", key = {"run", "r"})
    @ShellMethodAvailability(value = "isRunQuizCommandAvailable")
    public String runQuizCommand() {
        quizService.setQuestions(this.quiz);
        quizService.runQuiz(this.quiz);
        this.isTestPassed = true;
        return "Тест пройден";
    }


    private Availability isRunQuizCommandAvailable() {
        return quiz == null ? Availability.unavailable("Сначала зарегистрируйтесь!") : Availability.available();
    }

    @ShellMethod(value = "show result of quiz command", key = {"shr", "result"})
    @ShellMethodAvailability(value = "isShowResultQuizCommandAvailable")
    public void showResultQuizCommand() {
        quizService.showResult(this.quiz);
    }



    private Availability isShowResultQuizCommandAvailable() {
        return isTestPassed == false ? Availability.unavailable("Сначала пройдите тест!") : Availability.available();
    }



}
