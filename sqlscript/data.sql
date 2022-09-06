/*
TRUNCATE TABLE books CASCADE;
TRUNCATE TABLE "covers" CASCADE;
TRUNCATE TABLE role CASCADE;
TRUNCATE TABLE users CASCADE;
TRUNCATE TABLE status CASCADE;
TRUNCATE TABLE orders CASCADE;
TRUNCATE TABLE order_infos CASCADE;
*/

INSERT INTO "covers" ("name")
VALUES ('SOFT'),
		('HARD'),
		('SPECIAL');
	

INSERT INTO books (title, author, isbn, pages, price, cover_id)
VALUES ('The Pilgrim’s Progress', 'John Bunyan', '978-985-581-391-1', 365, 56.35, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Robinson Crusoe', 'Daniel Defoe', '968-985-581-391-1', 320, 56, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('Gulliver’s Travels', 'Jonathan Swift', '988-985-581-391-1', 562, 56.35, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SPECIAL')),
		('Tom Jones', 'Henry Fielding', '999-985-581-391-1', 421, 23.21, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('The Life and Opinions of Tristram Shandy, Gentleman', 'Laurence Sterne', '899-985-581-391-1', 199, 7.2, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Emma', 'Jane Austen', '898-985-581-391-1', 1017, 119.25, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('Frankenstein', 'Mary Shelley', '897-985-581-391-1', 114, 55, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Nightmare Abbey', 'Thomas Love Peacock', '896-985-581-391-1', 185, 23.35, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('The Narrative of Arthur Gordon Pym of Nantucket', 'Edgar Allan Poe', '865-985-581-391-1', 251, 95.17, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('Sybil', 'Benjamin Disraeli', '851-985-581-391-1', 320, 115.05, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Jane Eyre', 'Charlotte Brontë', '850-985-581-391-1', 241, 95.08, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SPECIAL')),
		('Wuthering Heights', 'Emily Brontë', '849-985-581-391-1', 252, 6.5, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Vanity Fair', 'William Thackeray', '848-985-581-391-1', 195, 3.99, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SPECIAL')),
		('David Copperfield', 'Charles Dickens', '847-985-581-391-1', 451, 99.99, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('The Scarlet Letter', 'Nathaniel Hawthorne', '846-985-581-391-1', 652, 100, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('Moby-Dick', 'Herman Melville', '845-985-581-391-1', 662, 51.65, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Alice’s Adventures in Wonderland', 'Lewis Carroll', '844-985-581-391-1', 777, 21.82, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('The Moonstone', 'Wilkie Collins', '843-985-581-391-1', 115, 56.35, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'HARD')),
		('Little Women', 'Louisa May Alcott', '842-985-581-391-1', 198, 36.42, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT')),
		('Middlemarch', 'George Eliot', '841-985-581-391-1', 196, 9.99, (SELECT c.cover_id FROM "covers" c WHERE "name" = 'SOFT'));


INSERT INTO "role" ("name")
VALUES ('ADMIN'),
		('MANAGER'),
		('USER');

INSERT INTO users (first_name, last_name, email, "password", role_id)
VALUES ('Nick', 'Biden', 'biden@gmail.us', 'qwerty', (SELECT r.role_id FROM "role" r WHERE r."name" = 'ADMIN')),
		('Mike', 'Scholz', 'scholz@gmail.de', 'password', (SELECT r.role_id FROM "role" r WHERE r."name" = 'MANAGER')),
		('Joseph', 'Black', 'black@yandex.ru', 'hardpassword', (SELECT r.role_id FROM "role" r WHERE r."name" = 'MANAGER')),
		('Donald', 'Duda', 'duda@gmail.pl', 'dvjkshkvgjjsfd', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Vladimir', 'Orban', 'orban@gmail.hu', 'qwerty', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Alexandr', 'Zelenskiy', 'zelenkiy@gmail.ua', 'kdjvskdj', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Gitanas', 'Kind', 'kind@gmail.com', 'ytrewq', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Arturs', 'Nauseda', 'nauseda@gmail.lt', 'asdfghj', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Alar', 'Makron', 'makron@gmail.fr', 'qwerty', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Emmanual', 'Karis', 'karis@gmail.ee', 'qwerty', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Alexander', 'Benet', 'benet@gmail.il', 'doogrepus', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Ilham', 'Pashinian', 'pashinian@gmail.am', 'ararat', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Recep ', 'Aliev', 'aliev@gmail.az', 'qazxsw', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Ilham', 'Erdogan', 'erdogan@gmail.tr', 'bayraktar', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Justin', 'Johnson', 'johnson@gmail.gb', 'London', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Boris', 'Triudo', 'triudo@gmail.ca', 'quebec', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Mario', 'Sarkozi', 'sarkozi@gmail.fr', 'svgsdjvg', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Nikola', 'Dragi', 'dragi@gmail.it', 'napoli', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Yuri', 'Gagarin', 'space@gmail.com', 'poehali', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Elon', 'Mask', 'tesla@gmail.com', 'spaceX', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER')),
		('Nikolo', 'Tesla', 'electricity@gmail.com', 'induction', (SELECT r.role_id FROM "role" r WHERE r."name" = 'USER'));

	
INSERT INTO status ("name")
VALUES ('PENDING'),
	('PAID'),
	('DELIVERED'),
	('CANCELED');

INSERT INTO orders (user_id, status_id, total_cost)
VALUES ((SELECT u.user_id FROM users u WHERE u.first_name = 'Nikolo'), (SELECT s.status_id FROM status s WHERE s."name" = 'PAID'), 123.11);


