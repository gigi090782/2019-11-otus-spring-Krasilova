package ru.krasilova.otus.spring.homework8.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.krasilova.otus.spring.homework8.models.Author;
import ru.krasilova.otus.spring.homework8.repositories.BookCustomRepository;
import ru.krasilova.otus.spring.homework8.repositories.BookRepository;


@Component
@RequiredArgsConstructor
public class AuthorCascadeDeleteEventsListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        bookRepository.removeBooksByAuthorId(id);
    }
}
