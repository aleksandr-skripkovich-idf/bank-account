--liquibase formatted sql

--changeset aleksandr:init-user-table
INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Viktoriya', '06.08.1997', 'Viktoriya_Secret228');

INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Aleksandr', '22.10.1994', 'Aleksandr_Great322');

INSERT INTO "user" (name, date_of_birth, password)
VALUES ('Leya', '13.02.2023', 'LeyaHappyDog');