CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount INTEGER NOT NULL CHECK (amount > 0),
    merchant VARCHAR(255) NOT NULL,
    tenpista_name VARCHAR(255) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
