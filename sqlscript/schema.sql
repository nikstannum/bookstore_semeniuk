/*
CREATE DATABASE bookstore;
DROP DATABASE bookstore
*/

/*
DROP TABLE IF EXISTS order_infos CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS "covers" CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS role CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS status CASCADE;

DROP TABLE IF EXISTS order_infos;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS "covers";
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS status;
*/

CREATE TABLE IF NOT EXISTS "covers" (
	cover_id BIGSERIAL PRIMARY KEY,
	"name" VARCHAR (10) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
	book_id BIGSERIAL PRIMARY KEY,
  title VARCHAR (75) NOT NULL,
  author VARCHAR (35) NOT NULL,
  isbn VARCHAR (30) NOT NULL,
  pages INT2,
  price NUMERIC(6,2),
  cover_id BIGINT NOT NULL REFERENCES "covers",
  deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS "role" (
	role_id BIGSERIAL PRIMARY KEY,
	"name" VARCHAR (10) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
	user_id BIGSERIAL PRIMARY KEY,
	first_name VARCHAR (30),
	last_name VARCHAR (30),
	email VARCHAR (40) NOT NULL,
	"password" VARCHAR (40) NOT NULL,
	role_id BIGINT NOT NULL REFERENCES ROLE,
	deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS status (
	status_id BIGSERIAL PRIMARY KEY,
	"name" VARCHAR (20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
	order_id BIGSERIAL PRIMARY KEY,
	user_id BIGINT NOT NULL REFERENCES users,
	status_id BIGINT REFERENCES status,
	total_cost NUMERIC (10,2),
	deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS order_infos (
	order_infos_id BIGSERIAL PRIMARY KEY,
	book_id BIGINT NOT NULL REFERENCES books,
	order_id BIGINT NOT NULL REFERENCES orders,
	book_quantity BIGINT,
	book_price NUMERIC (6,2)
);

