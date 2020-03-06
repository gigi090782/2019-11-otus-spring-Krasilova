package ru.krasilova.otus.spring.homework13.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.krasilova.otus.spring.homework13.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    boolean existsByUsername(String username);
}