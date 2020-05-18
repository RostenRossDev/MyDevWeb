INSERT INTO `users`(username, password, enabled) VALUES('CosRoma','$2a$10$zUuA2Rc2HJBotCaJUusja.H8HOTWZ8ab/PIUpsmNN4LBJe6oySuCC',1);

/*populate table authorities*/

INSERT INTO `authorities` (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES (1, 'ROLE_ADMIN');
