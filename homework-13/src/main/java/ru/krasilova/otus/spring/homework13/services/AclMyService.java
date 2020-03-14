package ru.krasilova.otus.spring.homework13.services;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.ObjectIdentity;
import ru.krasilova.otus.spring.homework13.models.Book;

public interface AclMyService {
    void addPermission(Book book);

    void deletePermission(Book book);
}
