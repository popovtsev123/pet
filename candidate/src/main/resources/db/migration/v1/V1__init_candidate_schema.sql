CREATE TABLE candidates.candidate
(
    id         UUID PRIMARY KEY                     DEFAULT uuid_generate_v4(),
    created    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (now() AT TIME ZONE 'utc'),
    email      VARCHAR(1024)               NOT NULL,
    level      VARCHAR(1024)               NOT NULL,
    first_name VARCHAR(64)                 NOT NULL,
    last_name  VARCHAR(64)                 NOT NULL,
    experience    VARCHAR(3)               NOT NULL,
    current_salary  VARCHAR(12)            NOT NULL
);