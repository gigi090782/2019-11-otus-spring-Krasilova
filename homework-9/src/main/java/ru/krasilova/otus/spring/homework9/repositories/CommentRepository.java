package ru.krasilova.otus.spring.homework9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework9.models.Comment;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface CommentRepository  extends JpaRepository<Comment, Long>
{
    List<Comment> findByBookId(Long id);
    List<Comment> findAll();
    void deleteAllByBookId(long bookId);
}
