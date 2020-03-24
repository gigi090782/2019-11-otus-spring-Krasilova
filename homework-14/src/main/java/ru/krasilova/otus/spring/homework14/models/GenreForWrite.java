package ru.krasilova.otus.spring.homework14.models;
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
public class GenreForWrite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public long getId() {
        return id;
    }
//
//    @OneToMany(targetEntity = BookForWrite.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "book_id")
//    private List<BookForWrite> books;


}
