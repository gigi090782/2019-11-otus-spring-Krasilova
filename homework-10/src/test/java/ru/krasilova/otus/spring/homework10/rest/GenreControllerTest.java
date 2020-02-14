package ru.krasilova.otus.spring.homework10.rest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.krasilova.otus.spring.homework10.repositories.BookRepository;
import ru.krasilova.otus.spring.homework10.repositories.GenreRepository;
import ru.krasilova.otus.spring.homework10.rest.GenreController;


@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties
@ComponentScan({"ru.krasilova.otus.spring.homework9.repositories"})

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GenreRepository repository;
    @MockBean
    private BookRepository bookRepository;


    @Test
    public void testListGenres() throws Exception {

        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/genres"));

        result.andExpect(MockMvcResultMatchers.view().name("listGenres"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("genres"));


    }
}