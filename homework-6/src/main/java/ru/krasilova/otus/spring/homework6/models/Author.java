package ru.krasilova.otus.spring.homework6.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

@Data
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
    private Date birthDate;

}

