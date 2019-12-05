package ru.krasilova.otus.spring.domain;

public class Student {

    private String firstname;
    private String lastname;

    public Student() {
    }

    public Student(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public void setFirstName(String firstname) {

        this.firstname = firstname;
    }

    public void setLastName(String lastname) {

        this.lastname = lastname;
    }

    public String getFirstName() {

        return firstname;
    }

    public String getLastName() {

        return lastname;
    }
}
