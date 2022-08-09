INSERT INTO books (title, author, isbn, pages, price)
VALUES ('The Pilgrim’s Progress', 'John Bunyan', '978-985-581-391-1', 365, 56.35),
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



INSERT INTO users (firstname, lastname, email, "password")
VALUES ('Nick', 'Biden', 'biden@gmail.us', 'qwerty'),
		('Mike', 'Scholz', 'scholz@gmail.de', 'password'),
		('Joseph', 'Black', 'black@yandex.ru', 'hardpassword'),
		('Donald', 'Duda', 'duda@gmail.pl', 'dvjkshkvgjjsfd'),
		('Vladimir', 'Orban', 'orban@gmail.hu', 'qwerty'),
		('Alexandr', 'Zelenskiy', 'zelenkiy@gmail.ua', 'kdjvskdj'),
		('Gitanas', 'Kind', 'kind@gmail.com', 'ytrewq'),
		('Arturs', 'Nauseda', 'nauseda@gmail.lt', 'asdfghj'),
		('Alar', 'Makron', 'makron@gmail.fr', 'qwerty'),
		('Emmanual', 'Karis', 'karis@gmail.ee', 'qwerty'),
		('Alexander', 'Benet', 'benet@gmail.il', 'doogrepus'),
		('Ilham', 'Pashinian', 'pashinian@gmail.am', 'ararat'),
		('Recep ', 'Aliev', 'aliev@gmail.az', 'qazxsw'),
		('Ilham', 'Erdogan', 'erdogan@gmail.tr', 'bayraktar'),
		('Justin', 'Johnson', 'johnson@gmail.gb', 'London'),
		('Boris', 'Triudo', 'triudo@gmail.ca', 'quebec'),
		('Mario', 'Sarkozi', 'sarkozi@gmail.fr', 'svgsdjvg'),
		('Nikola', 'Dragi', 'dragi@gmail.it', 'napoli'),
		('Yuri', 'Gagarin', 'space@gmail.com', 'poehali'),
		('Elon', 'Mask', 'tesla@gmail.com', 'spaceX'),
		('Nikolo', 'Tesla', 'electricity@gmail.com', 'induction');


INSERT INTO book_cover (book_id)
(SELECT b.book_id  FROM books b);