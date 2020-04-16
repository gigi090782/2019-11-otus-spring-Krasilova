package ru.krasilova.otus.spring.homework6.repositories;

import ru.krasilova.otus.spring.homework6.models.Comment;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.*;

@Transactional
@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList().size();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findByBookId(Long id) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                        "from Comment c inner join fetch c.book b " +
                        "where b.id = :id",
                Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Comment> findByBookName(String name) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                        "from  Comment c " +
                        " inner join fetch c.book b " +
                        "where b.name = :name",
                Comment.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query =
                em.createQuery("select c from Comment c ", Comment.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }
}
