package ru.krasilova.otus.spring.homework11.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.krasilova.otus.spring.homework11.LibraryApplication;
import ru.krasilova.otus.spring.homework11.models.Genre;
import ru.krasilova.otus.spring.homework11.repositories.BookRepository;
import ru.krasilova.otus.spring.homework11.repositories.GenreRepository;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = GenreRestController.class)

@DisplayName("RestController для работы с жанрами (web) должно")
public class GenreRestControllerWebTest {
    @MockBean
    private GenreRepository repository;

    @MockBean
    private BookRepository bookRepository;


    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("создавать жанр")
    public void testCreateGenre() {

        Genre genre = new Genre ("test");
        Mono<Genre> genreMono = Mono.just(genre);
        when(repository.save(genre)).thenReturn(genreMono);

        webClient.post()
                .uri("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(genre), Genre.class)
                .exchange()
                .expectStatus().isCreated();

    }

    @Test
    @DisplayName("получать жанр по наименованию")
    void testGetGenreByName() {
        Genre genre =new Genre("test");
        Mono<Genre> genreMono = Mono.just(genre);

        Mockito
                .when(repository.findByName("test"))
                .thenReturn(genreMono);

        webClient.get().uri("/api/genres/find/{name}", "test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Genre.class);

        Mockito.verify(repository, times(1)).findByName("test");
    }
}