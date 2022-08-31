
CREATE TABLE ban_reasons
(
    id          serial PRIMARY KEY NOT NULL,
    reason_name VARCHAR(30)        NOT NULL
);

CREATE TABLE ban_list
(
    id            bigserial PRIMARY KEY NOT NULL,
    target_id     BIGINT                NOT NULL,
    author_id     BIGINT                NOT NULL,
    ban_date      timestamp without time zone NOT NULL,
    ban_reason_id TINYINT               NOT NULL,
    ban_end_date  timestamp without time zone NOT NULL,
    FOREIGN KEY (target_id) REFERENCES users (id),
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (ban_reason_id) REFERENCES ban_reasons (id)
);

CREATE TABLE complaint_list
(
    id            bigserial PRIMARY KEY NOT NULL,
    target_id     BIGINT                NOT NULL,
    author_id     BIGINT                NOT NULL,
    content       TEXT DEFAULT '',
    creation_date timestamp without time zone NOT NULL,
    FOREIGN KEY (target_id) REFERENCES users (id),
    FOREIGN KEY (author_id) REFERENCES users (id),
);