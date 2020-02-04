package ru.krasilova.otus.spring.homework9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.GsonBuilderUtils;
import ru.krasilova.otus.spring.homework9.models.Book;
import ru.krasilova.otus.spring.homework9.repositories.BookRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication

public class LibraryApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LibraryApplication.class);

    }


}
