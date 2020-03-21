package ru.krasilova.otus.spring.homework14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.krasilova.otus.spring.homework14.repositories.AuthorRepository;

import java.io.IOException;

@SpringBootApplication

public class LibraryApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
