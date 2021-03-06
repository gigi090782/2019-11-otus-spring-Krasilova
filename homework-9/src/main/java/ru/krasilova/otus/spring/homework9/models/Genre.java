package ru.krasilova.otus.spring.homework9.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<Book> books;

    public Genre(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public long getId() {
        return id;
    }
}
