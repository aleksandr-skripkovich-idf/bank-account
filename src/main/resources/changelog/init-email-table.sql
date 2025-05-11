--liquibase formatted sql

--changeset aleksandr:init-email-table
INSERT INTO email_data (user_id, email)
VALUES (1, 'test1@mail.ru');

INSERT INTO email_data (user_id, email)
VALUES (1, 'test2@mail.ru');

INSERT INTO email_data (user_id, email)
VALUES (2, 'test3@mail.ru');

INSERT INTO email_data (user_id, email)
VALUES (2, 'test4@mail.ru');

INSERT INTO email_data (user_id, email)
VALUES (3, 'test5@mail.ru');

INSERT INTO email_data (user_id, email)
VALUES (3, 'test6@mail.ru');