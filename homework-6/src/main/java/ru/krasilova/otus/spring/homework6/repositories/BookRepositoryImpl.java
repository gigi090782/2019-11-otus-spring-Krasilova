package ru.krasilova.otus.spring.homework6.repositories;


import org.springframework.stereotype.Repository;
import ru.krasilova.otus.spring.homework6.models.Book;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Transactional
@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;




    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b  " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Book> findAllWithAllInfo() {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b  " +
                        "join fetch b.author " +
                        "join fetch b.genre ",
                Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public List<Book> findAllByAuthorID(Long id) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b" +
                        " join fetch b.comments " +
                        "where b.author_id = :id",
                Book.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Book> findAllByAuthorLastName(String lastName) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        " inner join Author a on a.id = b.author_id " +
                        "where a.lastName = :lastName",
                Book.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<Book> findAllByGenreName(String genreName) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        " inner join Genre g on g.id = b.genre_id " +
                        " join fetch b.comments " +
                        "where g.Name = :genreName",
                Book.class);
        query.setParameter("genreName", genreName);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
