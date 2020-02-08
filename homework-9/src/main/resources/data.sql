
insert into GENRES(name)
values ('Фантастика'), ('Роман'), ('Детектив');

insert into AUTHORS(firstname,secondname,lastname,birthdate)
values ('Агата','Мэри Кларисса','Кристи', '1890-09-15'), ('Лев','Николаевич','Толстой', '1817-09-09'),
('Тест','Тестович','Тестов', '1900-01-01');

insert into BOOKS (name, genre_id, author_id)
values ('Загадочное происшествие в Стайлзе', 3,1),('Война и мир', 2,2);

insert into COMMENTS ( text, book_id )
values ('Отличный детектив', 1),('Подзатянуто', 2),('Комментарий',1);


