
CREATE OR REPLACE FUNCTION SelectUserAndRolesByEmail(
    UserEmail VARCHAR (30)
)
    RETURNS TABLE (
        id BIGINT,
        first_name VARCHAR(50),
        last_name VARCHAR(50),
        email VARCHAR(70),
        password VARCHAR(255),
        avatar_link VARCHAR,
        status_name VARCHAR(40),
        activation_code VARCHAR(255),
        balance INT,
        date timestamp without time zone,
        service_count INT,
        order_count INT,
        complaint_count SMALLINT,
        description TEXT,
        inst_link VARCHAR,
        vk_link VARCHAR,
        role VARCHAR(15)
        )
    LANGUAGE SQL AS $$
SELECT users.id,
       users.first_name,
       users.last_name,
       users.email,
       users.password,
       users.avatar_link,
       user_status.status_name,
       users.activation_code,
       users.balance,
       users.date,
       users.service_count,
       users.order_count,
       users.complaint_count,
       users.description,
       users.inst_link,
       users.vk_link,
       roles.name as role
FROM users
         JOIN roles ON users.role_id = roles.id
         JOIN user_status ON users.status_id = user_status.id
WHERE users.email = UserEmail;
$$;

CREATE OR REPLACE FUNCTION SelectUserById(
    UserId BIGINT
)
    RETURNS TABLE (
        id BIGINT,
        first_name VARCHAR(50),
        last_name VARCHAR(50),
        email VARCHAR(70),
        password VARCHAR(255),
        avatar_link VARCHAR,
        status_name VARCHAR(40),
        activation_code VARCHAR(255),
        balance INT,
        date timestamp without time zone,
        service_count INT,
        order_count INT,
        complaint_count SMALLINT,
        description TEXT,
        inst_link VARCHAR,
        vk_link VARCHAR,
        role VARCHAR(15)
        )
    LANGUAGE SQL AS $$
SELECT users.id,
       users.first_name,
       users.last_name,
       users.email,
       users.password,
       users.avatar_link,
       user_status.status_name,
       users.activation_code,
       users.balance,
       users.date,
       users.service_count,
       users.order_count,
       users.complaint_count,
       users.description,
       users.inst_link,
       users.vk_link,
       roles.name as role
FROM users
         JOIN roles ON users.role_id = roles.id
         JOIN user_status ON users.status_id = user_status.id
WHERE users.id = UserId;
$$;

CREATE OR REPLACE FUNCTION SelectServicesByUserId( --Создание процедуры
    UserId BIGINT
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
    )
    language sql AS $$
    SELECT service_list.id,
           service_list.service_name,
           service_type.service_type,
           service_list.cost,
           service_list.purchases,
           service_list.description,
           service_list.photo_links,
           service_list.creation_date,
           service_list.update_date,
           service_list.author_id,
           users.first_name,
           users.last_name,
           users.avatar_link
    FROM service_list
        JOIN service_type ON service_list.service_type_id = service_type.id
        JOIN users ON service_list.author_id = users.id
    WHERE service_list.author_id = UserId; -- тело процедуры
$$;


CREATE OR REPLACE FUNCTION SelectServiceById( --Создание процедуры
    ServiceId BIGINT
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
        )
    language sql AS $$
SELECT service_list.id,
       service_list.service_name,
       service_type.service_type,
       service_list.cost,
       service_list.purchases,
       service_list.description,
       service_list.photo_links,
       service_list.creation_date,
       service_list.update_date,
       service_list.author_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM service_list
         JOIN service_type ON service_list.service_type_id = service_type.id
         JOIN users ON service_list.author_id = users.id
WHERE service_list.id = ServiceId; -- тело процедуры
$$;



CREATE OR REPLACE FUNCTION SelectTopServicesLimit8( --Создание процедуры
    Page INT
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
        )
    language sql AS $$
SELECT service_list.id,
       service_list.service_name,
       service_type.service_type,
       service_list.cost,
       service_list.purchases,
       service_list.description,
       service_list.photo_links,
       service_list.creation_date,
       service_list.update_date,
       service_list.author_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM service_list
         JOIN service_type ON service_list.service_type_id = service_type.id
         JOIN users ON service_list.author_id = users.id
ORDER BY service_list.purchases DESC LIMIT 8 OFFSET 8*Page; -- тело процедуры
$$;


CREATE OR REPLACE FUNCTION SelectServicesByName( --Создание процедуры
    ServiceName VARCHAR,
    Page INT DEFAULT 0
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
        )
    language sql AS $$
SELECT service_list.id,
       service_list.service_name,
       service_type.service_type,
       service_list.cost,
       service_list.purchases,
       service_list.description,
       service_list.photo_links,
       service_list.creation_date,
       service_list.update_date,
       service_list.author_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM service_list
         JOIN service_type ON service_list.service_type_id = service_type.id
         JOIN users ON service_list.author_id = users.id
WHERE service_list.service_name LIKE concat('%', ServiceName, '%') ORDER BY service_list.update_date DESC LIMIT 8 OFFSET 8*Page; -- тело процедуры
$$;

CREATE OR REPLACE FUNCTION SelectServicesByCategory( --Создание процедуры
    ServiceCategory VARCHAR,
    Page INT DEFAULT 0
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
        )
    language sql AS $$
SELECT service_list.id,
       service_list.service_name,
       service_type.service_type,
       service_list.cost,
       service_list.purchases,
       service_list.description,
       service_list.photo_links,
       service_list.creation_date,
       service_list.update_date,
       service_list.author_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM service_list
         JOIN service_type ON service_list.service_type_id = service_type.id
         JOIN users ON service_list.author_id = users.id
WHERE service_type.service_type LIKE concat('%', ServiceCategory, '%') ORDER BY service_list.update_date DESC LIMIT 8 OFFSET 8*Page; -- тело процедуры
$$;


CREATE OR REPLACE FUNCTION SelectServicesByAuthorName( --Создание процедуры
    AuthorName  VARCHAR
)
    RETURNS TABLE(
        id BIGINT,
        service_name VARCHAR,
        service_type VARCHAR,
        cost INT,
        purchases INT,
        description TEXT,
        photo_links TEXT[],
        creation_date timestamp without time zone,
        update_date timestamp without time zone,
        author_id BIGINT,
        author_firstname VARCHAR,
        author_lastname VARCHAR,
        author_avatar VARCHAR
        )
    language sql AS $$
SELECT service_list.id,
       service_list.service_name,
       service_type.service_type,
       service_list.cost,
       service_list.purchases,
       service_list.description,
       service_list.photo_links,
       service_list.creation_date,
       service_list.update_date,
       service_list.author_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM service_list
         JOIN service_type ON service_list.service_type_id = service_type.id
         JOIN users ON service_list.author_id = users.id
WHERE users.first_name LIKE concat('%', AuthorName,'%') OR users.last_name LIKE concat('%', AuthorName,'%')  ORDER BY service_list.update_date DESC LIMIT 8; -- тело процедуры
$$;


CREATE OR REPLACE FUNCTION SelectOrderById( --Создание процедуры
    OrderId BIGINT
)
    RETURNS TABLE(
        id BIGINT,
        creation_date timestamp without time zone,
        order_status VARCHAR,
        service_id BIGINT,
        service_name VARCHAR,
        content TEXT,
        photo_links TEXT[],
        orderer_id BIGINT,
        orderer_firstname VARCHAR,
        orderer_lastname VARCHAR,
        orderer_avatar VARCHAR
        )
    language sql AS $$
SELECT order_list.id as order_id,
       order_list.creation_date,
       order_status.status_name,
       order_list.service_id,
       service_list.service_name,
       order_list.content,
       service_list.photo_links,
       order_list.orderer_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM order_list JOIN order_status ON order_list.order_status_id = order_status.id
                JOIN users ON users.id = order_list.orderer_id
                JOIN service_list ON service_list.id = order_list.service_id
WHERE order_list.id = OrderId;
$$;

CREATE OR REPLACE FUNCTION SelectOrdersByUser( --Создание процедуры
    UserId BIGINT
)
    RETURNS TABLE(
        id BIGINT,
        creation_date timestamp without time zone,
        order_status VARCHAR,
        service_id BIGINT,
        service_name VARCHAR,
        content TEXT,
        photo_links TEXT[],
        orderer_id BIGINT,
        orderer_firstname VARCHAR,
        orderer_lastname VARCHAR,
        orderer_avatar VARCHAR
        )
    language sql AS $$
SELECT order_list.id as order_id,
       order_list.creation_date,
       order_status.status_name,
       order_list.service_id,
       service_list.service_name,
       order_list.content,
       service_list.photo_links,
       order_list.orderer_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM order_list JOIN order_status ON order_list.order_status_id = order_status.id
                JOIN service_list ON service_list.id = order_list.service_id
                JOIN users ON users.id = service_list.author_id
WHERE order_list.orderer_id = UserId;
$$;

CREATE OR REPLACE FUNCTION SelectOrdersByAuthor( --Создание процедуры
    UserId BIGINT
)
    RETURNS TABLE(
        id BIGINT,
        creation_date timestamp without time zone,
        order_status VARCHAR,
        service_id BIGINT,
        service_name VARCHAR,
        content TEXT,
        photo_links TEXT[],
        orderer_id BIGINT,
        orderer_firstname VARCHAR,
        orderer_lastname VARCHAR,
        orderer_avatar VARCHAR
        )
    language sql AS $$
SELECT order_list.id as order_id,
       order_list.creation_date,
       order_status.status_name,
       order_list.service_id,
       service_list.service_name,
       order_list.content,
       service_list.photo_links,
       order_list.orderer_id,
       users.first_name,
       users.last_name,
       users.avatar_link
FROM order_list JOIN order_status ON order_list.order_status_id = order_status.id
                JOIN service_list ON service_list.id = order_list.service_id
                JOIN users ON users.id = order_list.orderer_id
WHERE service_list.author_id = UserId;
$$;