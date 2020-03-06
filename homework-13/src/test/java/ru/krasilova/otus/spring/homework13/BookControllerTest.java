package ru.krasilova.otus.spring.homework13;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.krasilova.otus.spring.homework13.models.Book;
import ru.krasilova.otus.spring.homework13.repositories.*;
import ru.krasilova.otus.spring.homework13.rest.BookController;
import ru.krasilova.otus.spring.homework13.services.UserServiceImpl;
import javax.sql.DataSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ComponentScan({"ru.krasilova.otus.spring.homework13.services","ru.krasilova.otus.spring.homework13.security"})
@WebMvcTest(BookController.class)
@ContextConfiguration
@DisplayName("Тест BookController")
public class BookControllerTest  {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private DataSource dataSource;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    @WithMockUser(username = "admin" , password = "2")
    public void
    givenUserAdmin_whenFindAllBooks_thenReturnTwoBooks() {
        List<Book> books = bookRepository.findAll();
        assertNotNull(books);
        assertEquals(2, books.size());
    }

}