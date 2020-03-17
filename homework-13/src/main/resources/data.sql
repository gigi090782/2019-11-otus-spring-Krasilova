
insert into GENRES(name)
values ('Фантастика'), ('Роман'), ('Детектив');

insert into AUTHORS(firstname,secondname,lastname,birthdate)
values ('Агата','Мэри Кларисса','Кристи', '1890-09-15'), ('Лев','Николаевич','Толстой', '1817-09-09'),
('Тест','Тестович','Тестов', '1900-01-01');

insert into BOOKS (name, genre_id, author_id)
values ('Загадочное происшествие в Стайлзе', 3,1),('Война и мир', 2,2);

insert into COMMENTS ( text, book_id )
values ('Отличный детектив', 1),('Подзатянуто', 2),('Комментарий',1);


insert into USERS (  username, password, accountnonexpired,accountnonlocked,credentialsnonexpired,enabled  )
values ('admin','2', true,true,true,true ),('user','1', true,true,true,true  ),('test','1', true,true,true,true  );



INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin'),
(2, 1, 'user'),
(3, 1, 'test');



INSERT INTO acl_class (id, class) VALUES
(1, 'ru.krasilova.otus.spring.homework13.models.Author'),
(2, 'ru.krasilova.otus.spring.homework13.models.Book'),
(3, 'ru.krasilova.otus.spring.homework13.models.Comment'),
(4, 'ru.krasilova.otus.spring.homework13.models.Genre');


INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 2, 1, NULL, 1, 0),
(2, 2, 2, NULL, 1, 0);


INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 3, 1, 0, 1, 1),
(4, 1, 4, 3, 2, 0, 1, 1),
(5, 1, 5, 2, 1, 1, 1, 1),
(6, 2, 2, 1, 1, 1, 1, 1),
(7, 2, 3, 1, 2, 1, 1, 1),
(8, 2, 4, 2, 1, 0, 1, 1),
(9, 2, 5, 3, 1, 0, 1, 1);

