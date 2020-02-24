package ru.krasilova.otus.spring.homework11.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.krasilova.otus.spring.homework11.models.Genre;
import ru.krasilova.otus.spring.homework11.repositories.GenreRepository;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@DisplayName("RestController для работы с жанрами должно")
public class GenreRestControllerTest {
    @Autowired
    GenreRepository repository;

    @DisplayName("устанавливать ИД при сохранении")
    @Test
    public void shouldSetIdOnSave() {
        Mono<Genre> genreMono = repository.save(new Genre("test"));

        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }

    @DisplayName("находить жанры по наименованию")
    @Test
    public void shouldFindByName() {
        repository.save(new Genre("test")).subscribe();

        StepVerifier.create(
                repository.findByName("test")
        )
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }
    }
