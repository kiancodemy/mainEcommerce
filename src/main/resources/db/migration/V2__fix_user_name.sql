CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE users
(
    id         INTEGER                        NOT NULL,
    name       VARCHAR(255),
    last_name  VARCHAR(255),
    email      VARCHAR(255)                   NOT NULL,
    password   VARCHAR(255)                   NOT NULL,
    role       VARCHAR(255)                   NOT NULL,
    created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE "user" CASCADE;

DROP SEQUENCE user_seq CASCADE;