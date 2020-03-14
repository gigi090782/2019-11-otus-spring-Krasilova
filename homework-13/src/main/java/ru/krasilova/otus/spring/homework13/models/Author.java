package ru.krasilova.otus.spring.homework13.models;



import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstname", nullable = false, unique = false)
    private String firstName;
    @Column(name = "secondname", nullable = true, unique = false)
    private String secondName;
    @Column(name = "lastname", nullable = false, unique = false)
    private String lastName;
    @Column(name = "birthdate", nullable = false, unique = false)
    @DateTimeFormat(pattern = "dd.mm.yyyy")
    private String  birthDate;


    public Author(String firstName, String secondName,String lastName,String  birthDate)
    {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

    public String  getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String  birthDate) {
        this.birthDate = birthDate;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

