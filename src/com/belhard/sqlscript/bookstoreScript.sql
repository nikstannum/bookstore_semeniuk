-- bookstore script

create database bookstore;

create table book
(book_id BIGSERIAL primary key,
title VARCHAR (75),
author VARCHAR (35),
isbn VARCHAR (30) UNIQUE,
pages INT2,
price numeric(6,2) check (price > 0));

insert into book (title, author, isbn, pages, price)
values ('The Pilgrim’s Progress', 'John Bunyan', '978-985-581-391-1', 365, 56.35),
		('Robinson Crusoe', 'Daniel Defoe', '968-985-581-391-1', 320, 56),
		('Gulliver’s Travels', 'Jonathan Swift', '988-985-581-391-1', 562, 56.35),
		('Tom Jones', 'Henry Fielding', '999-985-581-391-1', 421, 23.21),
		('The Life and Opinions of Tristram Shandy, Gentleman', 'Laurence Sterne', '899-985-581-391-1', 199, 7.2),
		('Emma', 'Jane Austen', '898-985-581-391-1', 1017, 119.25),
		('Frankenstein', 'Mary Shelley', '897-985-581-391-1', 114, 55),
		('Nightmare Abbey', 'Thomas Love Peacock', '896-985-581-391-1', 185, 23.35),
		('The Narrative of Arthur Gordon Pym of Nantucket', 'Edgar Allan Poe', '865-985-581-391-1', 251, 95.17),
		('Sybil', 'Benjamin Disraeli', '851-985-581-391-1', 320, 115.05),
		('Jane Eyre', 'Charlotte Brontë', '850-985-581-391-1', 241, 95.08),
		('Wuthering Heights', 'Emily Brontë', '849-985-581-391-1', 252, 6.5),
		('Vanity Fair', 'William Thackeray', '848-985-581-391-1', 195, 3.99),
		('David Copperfield', 'Charles Dickens', '847-985-581-391-1', 451, 99.99),
		('The Scarlet Letter', 'Nathaniel Hawthorne', '846-985-581-391-1', 652, 100),
		('Moby-Dick', 'Herman Melville', '845-985-581-391-1', 662, 51.65),
		('Alice’s Adventures in Wonderland', 'Lewis Carroll', '844-985-581-391-1', 777, 21.82),
		('The Moonstone', 'Wilkie Collins', '843-985-581-391-1', 115, 56.35),
		('Little Women', 'Louisa May Alcott', '842-985-581-391-1', 198, 36.42),
		('Middlemarch', 'George Eliot', '841-985-581-391-1', 196, 9.99);