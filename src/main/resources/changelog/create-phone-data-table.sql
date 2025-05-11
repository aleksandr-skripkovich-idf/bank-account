--liquibase formatted sql

--changeset aleksandr:create-phone-data-table
CREATE TABLE IF NOT EXISTS phone_data (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    phone VARCHAR(13) UNIQUE,
    CONSTRAINT fk_phone_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE INDEX IF NOT EXISTS idx_user_id_phone ON phone_data (user_id, phone);

CREATE INDEX IF NOT EXISTS idx_phone ON phone_data (phone);