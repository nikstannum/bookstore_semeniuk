CREATE database bookstore;
--DROP database bookstore


CREATE TABLE if NOT EXISTS books
(book_id BIGSERIAL PRIMARY KEY,
title VARCHAR (75),
author VARCHAR (35),
isbn VARCHAR (30) UNIQUE,
pages INT2,
price NUMERIC(6,2) CHECK (price > 0));
--DROP TABLE if EXISTS books;


CREATE TABLE if NOT EXISTS users
(user_id BIGSERIAL PRIMARY KEY,
firstName VARCHAR (30),
lastName VARCHAR (30),
email VARCHAR (40) UNIQUE NOT NULL,
"password" VARCHAR (40) NOT NULL);
--DROP TABLE if EXISTS users;



