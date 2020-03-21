package ru.krasilova.otus.spring.homework14.models;


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
    private String birthDate;

    @DBRef
    private List<Book> books;


    public Author(String firstname, String secondname, String lastname, String birthdate) {
        this.firstName = firstname;
        this.secondName = secondname;
        this.lastName = lastname;
        this.birthDate = birthdate;
    }

    public String getId() {
        return id;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
       /* String booksStr = "";
        for (var b:books)
        {
            booksStr += b.toString()+" ";
        }

        */
        return "authorId: " + this.getId() + " , authorFirstName: " + this.getFirstName() + ", authorSecondName: "
                + this.getSecondName() + ", authorLastName: " + this.getLastName()
                +  ", authorBirthDate: " + this.getBirthDate();

    }

}

