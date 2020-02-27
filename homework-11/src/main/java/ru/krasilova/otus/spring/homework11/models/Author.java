package ru.krasilova.otus.spring.homework11.models;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;




import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class Author {
    @Id
    private String id;

    @JsonProperty("firstname")
    @Field(name = "firstname")
    private String firstName;

    @JsonProperty("secondname")
    @Field(name = "secondname")
    private String secondName;

    @JsonProperty("lastname")
    @Field(name = "lastname")
    private String lastName;

    @Field(name = "birthdate")
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    private String birthDate;

    @DBRef
    private List<Book> books;


    public Author (String firstname, String secondname, String lastname, String birthdate)
    {
        this.firstName = firstname;
        this.secondName = secondname;
        this.lastName = lastname;
        this.birthDate = birthdate;
    }



    public void setBirthdate(String birthdate)
    {
        this.birthDate = birthdate;
    }


    public String getBirthdate() {
        return birthDate;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}

