--liquibase formatted sql

--changeset aleksandr:create-user-table
CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(500),
    date_of_birth VARCHAR(16),
    password VARCHAR(500)
);