package ru.krasilova.otus.spring.homework8.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import ru.krasilova.otus.spring.homework8.models.Author;
import ru.krasilova.otus.spring.homework8.models.Book;
import ru.krasilova.otus.spring.homework8.models.Comment;
import ru.krasilova.otus.spring.homework8.models.Genre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@ChangeLog(order = "001")
public class InitLibaryDBData {

    private Genre genreDetective;
    private Genre genreNovel;
    private Book book1;
    private Book book2;
    private Book book3;
    private Author authorCristy;
    private Author authorTolstoy;

    @ChangeSet(order = "000", id = "dropDB", author = "gigi", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initKnowledges", author = "gigi", runAlways = true)
    public void initKnowledges(MongoTemplate template) {
        genreNovel = template.save(new Genre("Роман"));
        genreDetective = template.save(new Genre("Детектив"));
        template.save(new Genre("Фантастика"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "gigi", runAlways = true)
    public void initSAuthors(MongoTemplate template) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new SimpleDateFormat("dd.MM.yyyy").parse("28.05.1967");
        template.save(new Author("Андрей", "Валентинович", "Жвалевский", date));
        date = new SimpleDateFormat("dd.MM.yyyy").parse("15.09.1890");
        authorCristy = template.save(new Author("Агата", "Мэри Кларисса", "Кристи", date));
        date = new SimpleDateFormat("dd.MM.yyyy").parse("09.09.1817");
        authorTolstoy = template.save(new Author("Лев", "Николаевич", "Толстой", date));




    }

    @ChangeSet(order = "003", id = "initBooks", author = "gigi", runAlways = true)
    public void initBooks(MongoTemplate template) {
        book1 = template.save(new Book("Загадочное происшествие в Стайлзе", authorCristy, genreDetective));
        book2 = template.save(new Book("Война и мир", authorTolstoy, genreNovel));
        book3 = template.save(new Book("И никого не стало", authorCristy, genreDetective));
    }

    @ChangeSet(order = "004", id = "initComments", author = "gigi", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(new Comment ("Отличный детектив", book1));
        template.save(new Comment ("Так себе детектив", book1));
        template.save(new Comment ("Подзатянуто", book2));
        template.save(new Comment ("Понравилась книга", book3));

    }


}
