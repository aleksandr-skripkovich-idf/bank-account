--liquibase formatted sql

--changeset aleksandr:create-email-data-table
CREATE TABLE IF NOT EXISTS email_data (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    email VARCHAR(200) UNIQUE,
    CONSTRAINT fk_email_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE INDEX idx_user_id_email on email_data (user_id, email);

CREATE INDEX idx_email on email_data (email);