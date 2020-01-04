insert into GENRES (`name`) values ('Фантастика');
insert into GENRES (`name`) values ('Роман');
insert into GENRES (`name`) values ('Детектив');
insert into AUTHORS (`firstname`,`secondname`,`lastname`,`birthdate`) values ('Агата','Мэри Кларисса','Кристи', '1890-09-15');
insert into AUTHORS (`firstname`,`secondname`,`lastname`,`birthdate`) values ('Лев','Николаевич','Толстой', '1817-09-09');
insert into BOOKS (`name`, genreid, authorid) values ('Загадочное происшествие в Стайлзе', 3,1);
insert into BOOKS (`name`, genreid, authorid) values ('Война и мир', 2,2);


