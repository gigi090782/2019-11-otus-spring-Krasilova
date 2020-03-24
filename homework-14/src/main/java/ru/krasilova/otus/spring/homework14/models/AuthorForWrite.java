package ru.krasilova.otus.spring.homework14.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class AuthorForWrite {
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
    private String birthDate;
//    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id")
//    private List<BookForWrite> books;

    public AuthorForWrite(String firstName, String secondName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthDate = birthDate;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }
}

