package ru.krasilova.otus.spring.homework8.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.krasilova.otus.spring.homework8.models.Book;
import ru.krasilova.otus.spring.homework8.repositories.CommentRepository;

@Component
@RequiredArgsConstructor
public class BookCascadeDeleteEventsListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Book> event) {
        super.onAfterDelete(event);
        val source = event.getSource();
        val id = source.get("_id").toString();
        commentRepository.removeCommentsByBookId(id);


    }
}

