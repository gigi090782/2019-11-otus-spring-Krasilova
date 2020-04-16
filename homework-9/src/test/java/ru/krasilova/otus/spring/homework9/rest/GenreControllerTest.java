package ru.krasilova.otus.spring.homework9.rest;

import static org.junit.jupiter.api.Assertions.*;

import lombok.val;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;
import ru.krasilova.otus.spring.homework9.repositories.GenreRepository;



@RunWith(SpringRunner.class)
@WebMvcTest(ru.krasilova.otus.spring.homework9.rest.GenreController.class)
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