package ru.krasilova.otus.spring.homework5.models;


import java.util.Date;

public class Author {

    private final String firstName;
    private final String secondName;
    private final String lastName;
    private final Date birthDate;
    private final long id;


    public Author(long id, String firstName, String secondName, String lastName, Date birthDate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        Date birthDateWithoutTime = birthDate;
        birthDateWithoutTime.setTime(0);
        this.birthDate = birthDateWithoutTime;
        this.id = id;

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

    public Date getBirthDate() {
        return birthDate;
    }

    public long getId() {
        return id;
    }
/*
    public void setId(long id) {
        this.id = id;
    }

 */
}
