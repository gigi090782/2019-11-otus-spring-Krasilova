package ru.krasilova.otus.spring.homework8.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection =  "books")
public class Book {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @DBRef
    private Author author;
    @DBRef
    private Genre genre;

    public Book (String name, Author author, Genre genre )
    {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

}
