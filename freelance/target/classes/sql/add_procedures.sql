CREATE OR REPLACE PROCEDURE AddUser(FirstName VARCHAR (50),
                         LastName VARCHAR (50),
                         Mail VARCHAR,
                         Pass_word VARCHAR ,
                         Datetime timestamp without time zone,
                         Avatar VARCHAR DEFAULT NULL,
                         About VARCHAR DEFAULT NULL,
                         InstLink VARCHAR DEFAULT NULL,
                         VkLink VARCHAR DEFAULT NULL )
    language sql AS $$
INSERT INTO users(first_name,
                  last_name,
                  email,
                  password,
                  date,
                avatar_link,
                description,
                inst_link,
                vk_link)
VALUES (FirstName,
        LastName,
        Mail,
        Pass_word,
        Datetime,
        Avatar,
        About,
        InstLink,
        VkLink);
$$;

CREATE OR REPLACE PROCEDURE AddService(ServiceName VARCHAR,
                            TypeId SMALLINT,
                            AuthorId BIGINT,
                            TextDescription TEXT,
                            PhotoUrls VARCHAR [],
                            Price INT
)
    LANGUAGE SQL AS $$
INSERT INTO service_list(service_name, service_type_id, author_id, cost, description, photo_links)
VALUES (ServiceName, TypeId, AuthorId, Price, TextDescription, PhotoUrls);
$$;

CREATE OR REPLACE PROCEDURE AddOrder(OrdererId BIGINT, ServiceId BIGINT)
    LANGUAGE SQL AS $$
INSERT INTO order_list(orderer_id, service_id) VALUES (OrdererId, ServiceId);
UPDATE service_list SET purchases = purchases + 1 WHERE service_list.id = ServiceId;
UPDATE users SET balance = (SELECT users.balance FROM users WHERE users.id = OrdererId) - (SELECT service_list.cost FROM service_list WHERE service_list.id = Serviceid)
WHERE users.id = OrdererId;
UPDATE users SET balance = (SELECT users.balance FROM users WHERE users.id = (SELECT service_list.author_id FROM service_list WHERE service_list.id = ServiceId)) + (SELECT service_list.cost FROM service_list WHERE service_list.id = Serviceid)
WHERE users.id = (SELECT service_list.author_id FROM service_list WHERE service_list.id = ServiceId);
$$;
