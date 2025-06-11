INSERT INTO tb_user (name, email, password, phone) VALUES ('admin', 'admin@admin.com', '$2a$10$wTEO.BK3y7A2BPvgcZJbveFAm258I4/1QJg9zX8/dikMUPC0/206a', '');
INSERT INTO tb_user (name, email, password, phone) VALUES ('Maria Brown', 'maria@gmail.com', '$2a$10$/sWSLSCwgBYURMta1zhJRuPRLspPCGPBnybS080ixwoPRJ2PYHmcK', '988888888');
INSERT INTO tb_user (name, email, password, phone) VALUES ('Alex Green','alex@gmail.com','$2a$10$kpcDH5zMKK1r9TMzStnZ7OxoGq0aZ4j9i3LWs.sYx5AifWugmG8S.','977777777');

INSERT INTO roles (authority) VALUES ('SCOPE_ADMIN');
INSERT INTO roles (authority) VALUES ('SCOPE_CLIENT');

INSERT INTO tb_users_roles (roles_id, user_id) VALUES (1,1);
INSERT INTO tb_users_roles (roles_id, user_id) VALUES (2,2);
INSERT INTO tb_users_roles (roles_id, user_id) VALUES (2,3);

INSERT INTO orders (moment, status, client_id) VALUES ('2024-10-11T19:53:07Z', 2, 2);
INSERT INTO orders (moment, status, client_id) VALUES ('2024-10-09T03:42:10Z', 1, 3);
INSERT INTO orders (moment, status, client_id) VALUES ('2024-10-05T15:21:22Z', 1, 2);

INSERT INTO genders (name) VALUES ('Romance');
INSERT INTO genders (name) VALUES ('Desenvolvimento Pessoal');
INSERT INTO genders (name) VALUES ('Ficção Cientifica');

INSERT INTO books (title, author, pages, isbn, price, img_url) VALUES ('Dom Casmurro', 'Machado de Assis', 208, '978_8594318602L', 24.90, '');
INSERT INTO books (title, author, pages, isbn, price, img_url) VALUES ('A arte da guerra', 'Sun Tzu', 80, '78_6584956230L', 49.90, '');
INSERT INTO books (title, author, pages, isbn, price, img_url) VALUES ('Diário estoico', 'Ryan Holiday', 496, '978_6555605556L', 64.90, '');
INSERT INTO books (title, author, pages, isbn, price, img_url) VALUES ('Box Duna: Primeira Trilogia', 'Frank Herbert', 1480, '978_6586064414L', 299.90, '');
INSERT INTO books (title, author, pages, isbn, price, img_url) VALUES ('Middlemarch', 'George Eliot', 880, '978_0141196893L', 149.90, '');

INSERT INTO book_gender(book_id, gender_id) VALUES (1, 1);
INSERT INTO book_gender(book_id, gender_id) VALUES (2, 2);
INSERT INTO book_gender(book_id, gender_id) VALUES (3, 2);
INSERT INTO book_gender(book_id, gender_id) VALUES (4,3);
INSERT INTO book_gender(book_id, gender_id) VALUES (5,1);

INSERT INTO Order_item(price, quantity, book_id, order_id) VALUES (24.90, 2, 1, 1);
INSERT INTO Order_item(price, quantity, book_id, order_id) VALUES (64.90, 1, 3, 1);
INSERT INTO Order_item(price, quantity, book_id, order_id) VALUES (64.90, 2, 3, 2);
INSERT INTO Order_item(price, quantity, book_id, order_id) VALUES (149.90, 2, 5, 3);

INSERT INTO payment (moment, order_Id) VALUES ('2024-10-11T20:53:07Z', 1);
