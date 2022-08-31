CREATE TABLE IF NOT EXISTS user_status
(
    id          serial PRIMARY KEY NOT NULL,
    status_name VARCHAR(10)        NOT NULL
);

INSERT INTO user_status(status_name) VALUES ('ACTIVE');
INSERT INTO user_status(status_name) VALUES ('NOT_ACTIVE');
INSERT INTO user_status(status_name) VALUES ('BANNED');

CREATE TABLE IF NOT EXISTS roles
(
    id   smallserial PRIMARY KEY NOT NULL,
    name VARCHAR(15)             NOT NULL
);

INSERT INTO roles(name) VALUES ('USER');
INSERT INTO roles(name) VALUES ('MODERATOR');
INSERT INTO roles(name) VALUES ('ADMIN');

CREATE TABLE IF NOT EXISTS users
(
    id              bigserial PRIMARY KEY NOT NULL,
    first_name      VARCHAR(20)           NOT NULL,
    last_name       VARCHAR(20)           NOT NULL,
    email           VARCHAR               NOT NULL
        UNIQUE,
    password        VARCHAR(255)          NOT NULL,
    avatar_link     VARCHAR                        DEFAULT NULL,
    status_id       SMALLINT           NOT NULL DEFAULT 1,
    activation_code VARCHAR(255),
    balance         INT                            DEFAULT 7000,
    date            timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    service_count   INT                            DEFAULT 0,
    description     TEXT                           DEFAULT NULL,
    inst_link       VARCHAR                        DEFAULT NULL,
    vk_link         VARCHAR                        DEFAULT NULL,
    order_count     INT                            DEFAULT 0,
    complaint_count SMALLINT                       DEFAULT 0,
    role_id         SMALLINT                       DEFAULT 1,
    FOREIGN KEY (status_id) REFERENCES user_status (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);



CREATE TABLE IF NOT EXISTS service_type
(
    id           smallserial PRIMARY KEY NOT NULL,
    service_type VARCHAR(30)             NOT NULL
);
INSERT INTO service_type(id, service_type) VALUES (1, 'MUSIC');
INSERT INTO service_type(id, service_type) VALUES (2, 'PAINTING');
INSERT INTO service_type(id, service_type) VALUES (3, 'DIGITAL_ART');
INSERT INTO service_type(id, service_type) VALUES (4, 'WEB_DESIGN');
INSERT INTO service_type(id, service_type) VALUES (5, '3D_MODELING');
INSERT INTO service_type(id, service_type) VALUES (6, 'PROGRAMMING');
INSERT INTO service_type(id, service_type) VALUES (7, 'OTHER');

CREATE TABLE IF NOT EXISTS service_list
(
    id              bigserial PRIMARY KEY NOT NULL,
    service_name    VARCHAR               NOT NULL,
    service_type_id SMALLINT              NOT NULL,
    author_id       bigint                NOT NULL,
    cost            INT                   NOT NULL,
    purchases       INT                   DEFAULT 0,
    description     TEXT                  DEFAULT NULL,
    photo_links     VARCHAR []                DEFAULT NULL,
    creation_date   timestamp without time zone DEFAULT CURRENT_DATE,
    update_date     timestamp without time zone DEFAULT CURRENT_DATE,
    FOREIGN KEY (service_type_id) REFERENCES service_type (id),
    FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS content
(
    id         bigserial PRIMARY KEY NOT NULL,
    path       VARCHAR               NOT NULL,
    service_id BIGINT                NOT NULL,
    FOREIGN KEY (service_id) REFERENCES service_list (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_status
(
    id          serial PRIMARY KEY NOT NULL,
    status_name VARCHAR(15)        NOT NULL
);

INSERT INTO order_status(status_name) VALUES ('HANDLING');
INSERT INTO order_status(status_name) VALUES ('IN_PROGRESS');
INSERT INTO order_status(status_name) VALUES ('SUCCESS');
INSERT INTO order_status(status_name) VALUES ('REJECT');

CREATE TABLE IF NOT EXISTS order_list
(
    id              bigserial PRIMARY KEY NOT NULL,
    orderer_id      BIGINT                NOT NULL,
    service_id      BIGINT                NOT NULL,
    content         TEXT                  DEFAULT NULL,
    creation_date   timestamp without time zone DEFAULT CURRENT_DATE,
    order_status_id SMALLINT               DEFAULT 1,
    FOREIGN KEY (service_id) REFERENCES service_list (id) ON DELETE RESTRICT,
    FOREIGN KEY (orderer_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (order_status_id) REFERENCES order_status (id)
);
