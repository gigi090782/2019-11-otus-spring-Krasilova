package ru.krasilova.otus.spring.homework8.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.krasilova.otus.spring.homework8.models.Genre;
import ru.krasilova.otus.spring.homework8.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class GenreCascadeDeleteEventsListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Genre> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeBooksByGenreId(id);
    }
}

