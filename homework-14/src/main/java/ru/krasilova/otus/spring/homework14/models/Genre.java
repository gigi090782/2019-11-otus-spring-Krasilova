package ru.krasilova.otus.spring.homework14.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public class Genre {
    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @DBRef
    private List<Book> books;

    public Genre(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    @Override
    public String toString() {
        return "genreId: " + id + ", name: " + name ;
    }

}
