package ru.krasilova.otus.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест команд shell ")
@SpringBootTest
class StudentsQuizShellTest {

    @Autowired
    private Shell shell;


    private static final String DEFAULT_FIRSTNAME = "Марина";
    private static final String DEFAULT_LASTNAME = "Красилова";
    private static final String COMMAND_REGISTER = "register";
    private static final String COMMAND_REGISTER_SHORT = "rqs";
    private static final String COMMAND_RUN = "run";
    private static final String COMMAND_RUN_EXPECTED_RESULT = "Тест пройден";
    private static final String COMMAND_REGISTER_PATTERN = "Начало опроса \nСтудент: %s %s\n";


    @DisplayName("должен возвращать приветствие для всех форм команды регистрации")
    @Test
    void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
        String res = (String) shell.evaluate(() -> COMMAND_REGISTER);
        assertThat(res).isEqualTo(String.format(COMMAND_REGISTER_PATTERN, DEFAULT_FIRSTNAME, DEFAULT_LASTNAME));

        res = (String) shell.evaluate(() -> COMMAND_REGISTER_SHORT);
        assertThat(res).isEqualTo(String.format(COMMAND_REGISTER_PATTERN, DEFAULT_FIRSTNAME, DEFAULT_LASTNAME));
    }

    @DisplayName("должен возвращать CommandNotCurrentlyAvailable если при попытке выполнения команды run студент не зарегистрировался")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    void shouldReturnCommandNotCurrentlyAvailableObjectWhenUserDoesNotRunAfterRegisterCommandEvaluated() {
        Object res = shell.evaluate(() -> COMMAND_RUN);
        assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
    }

}