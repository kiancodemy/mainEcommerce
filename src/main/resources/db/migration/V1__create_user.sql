CREATE SEQUENCE IF NOT EXISTS password_reset_token_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS revinfo_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE password_reset_token
(
    id         INTEGER                        NOT NULL,
    token      VARCHAR(255)                   NOT NULL,
    "user-id"  INTEGER                        NOT NULL,
    expires_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    used       BOOLEAN                        NOT NULL,
    CONSTRAINT pk_passwordresettoken PRIMARY KEY (id)
);

CREATE TABLE revchanges
(
    rev        BIGINT NOT NULL,
    entityname VARCHAR(255)
);

CREATE TABLE revinfo
(
    rev      BIGINT NOT NULL,
    revtstmp BIGINT,
    CONSTRAINT pk_revinfo PRIMARY KEY (rev)
);

CREATE TABLE "user"
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
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE password_reset_token
    ADD CONSTRAINT uc_passwordresettoken_token UNIQUE (token);

ALTER TABLE password_reset_token
    ADD CONSTRAINT "FK_PASSWORDRESETTOKEN_ON_USER-ID" FOREIGN KEY ("user-id") REFERENCES "user" (id);

ALTER TABLE revchanges
    ADD CONSTRAINT fk_revchanges_on_default_tracking_modified_entities_changelog FOREIGN KEY (rev) REFERENCES revinfo (rev);