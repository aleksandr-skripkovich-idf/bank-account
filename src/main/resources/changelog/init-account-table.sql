--liquibase formatted sql

--changeset aleksandr:init-account-table
INSERT INTO account (user_id, balance, start_deposit)
VALUES (1, 14.0, 228.22);

INSERT INTO account (user_id, balance, start_deposit)
VALUES (2, 155.5, 322.22);

INSERT INTO account (user_id, balance, start_deposit)
VALUES (3, 0, 232.11);