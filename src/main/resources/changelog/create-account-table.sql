--liquibase formatted sql

--changeset aleksandr:create-account-table
CREATE TABLE IF NOT EXISTS account (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    balance DECIMAL,
    start_deposit DECIMAL,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);