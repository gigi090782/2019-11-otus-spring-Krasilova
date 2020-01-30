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
@Document(collection =  "comments")
public class Comment {
    @Id
    private String id;
    @Field(name = "text")
    private String text;
    @DBRef
    private Book book;

    public Comment (String text, Book book)
    {this.text = text;
     this.book = book;
    }
}
