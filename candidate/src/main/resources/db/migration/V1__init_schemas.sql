CREATE SCHEMA IF NOT EXISTS candidate;
CREATE SCHEMA IF NOT EXISTS candidate_history;

CREATE TABLE IF NOT EXISTS candidate.candidate (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50),
    email VARCHAR(255) NOT NULL UNIQUE,
    experience INT,
    level VARCHAR(255) CHECK (level IN ('JUNIOR','MIDDLE','SENIOR')),
    current_salary NUMERIC(38,2)
    );

CREATE TABLE IF NOT EXISTS candidate_history.revinfo (
                                                         revision BIGSERIAL PRIMARY KEY,
                                                         revtstmp BIGINT NOT NULL
);