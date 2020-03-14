package ru.krasilova.otus.spring.homework12;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.krasilova.otus.spring.homework12.repositories.AuthorRepository;
import ru.krasilova.otus.spring.homework12.repositories.BookRepository;
import ru.krasilova.otus.spring.homework12.repositories.UserRepository;
import ru.krasilova.otus.spring.homework12.rest.AuthorController;
import ru.krasilova.otus.spring.homework12.security.SecurityConfig;
import ru.krasilova.otus.spring.homework12.services.UserService;
import ru.krasilova.otus.spring.homework12.services.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ComponentScan({"ru.krasilova.otus.spring.homework12.services"})
@WebMvcTest(AuthorController.class)
@DisplayName("Тест AuthorController")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;


    @WithMockUser(
            username = "admin",
            password = "admin1"
    )
    @Test
    @DisplayName("Получая информацию о пользователе, должен возвращать статус ОК")
    public void find_login_shouldSucceedWith200() throws Exception {
         mvc.perform(MockMvcRequestBuilders.get("/authors")).andExpect(status().isOk());
    }


    @Test
    @DisplayName("Не получая информацию о пользователе, должен делать редирект на форму логина")
    public void find_nologin_redirect_to_loginform() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().is3xxRedirection());
    }

}