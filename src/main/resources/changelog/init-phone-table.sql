--liquibase formatted sql

--changeset aleksandr:init-phone-table
INSERT INTO phone_data (user_id, phone)
VALUES (1, '79123151515');

INSERT INTO phone_data (user_id, phone)
VALUES (1, '79123141414');

INSERT INTO phone_data (user_id, phone)
VALUES (2, '79203202020');

INSERT INTO phone_data (user_id, phone)
VALUES (2, '79202201516');

INSERT INTO phone_data (user_id, phone)
VALUES (3, '79151156455');

INSERT INTO phone_data (user_id, phone)
VALUES (3, '79153111411');