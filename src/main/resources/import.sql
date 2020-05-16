INSERT INTO `users`(username, password, enabled) VALUES('andres','$2a$10$OJyrU2PvsoZQlIW/QTQjY.gxSWQ.D1fU3OK7RkbUpFjq9lKAlDpI.',1);
INSERT INTO `users`(username, password, enabled) VALUES('admin','$2a$10$z5T3.yyZ20aKFJAoeX3efeixy6HxQYSWPm1Qzvw75mWfjpHMQbMoK',1);

/*populate table authorities*/

INSERT INTO `authorities` (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO `authorities` (user_id, authority) VALUES (2, 'ROLE_ADMIN');
