package ru.krasilova.otus.spring.homework8.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    @Field(name = "firstname")
    private String firstName;
    @Field(name = "secondname")
    private String secondName;
    @Field(name = "lastname")
    private String lastName;
    @Field(name = "birthdate")
    private Date birthDate;

    @DBRef
    private List<Book> books;


    public Author (String firstname, String secondname, String lastname, Date birthdate)
    {
        this.firstName = firstname;
        this.secondName = secondname;
        this.lastName = lastname;
        this.birthDate = birthdate;
    }

}

