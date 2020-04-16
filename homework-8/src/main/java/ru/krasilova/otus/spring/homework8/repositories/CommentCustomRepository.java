package ru.krasilova.otus.spring.homework8.repositories;

public interface CommentCustomRepository {

    void removeCommentsByBookId(String id);
}
