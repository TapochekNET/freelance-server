CREATE OR REPLACE PROCEDURE changeUserStatus(UserId BIGINT, StatusId SMALLINT)
LANGUAGE SQL AS $$
    UPDATE users SET status_id = statusId WHERE users.id = UserId;
$$;

CREATE OR REPLACE PROCEDURE changeOrderStatus(OrderId BIGINT, StatusId SMALLINT, ContentLink TEXT DEFAULT NULL)
    LANGUAGE SQL AS $$
UPDATE order_list SET order_status_id = statusId, content = ContentLink WHERE order_list.id = OrderId;
$$;

CREATE OR REPLACE PROCEDURE uodateUserRating(UserId BIGINT)
LANGUAGE SQL AS $$
    UPDATE users SET order_count = order_count + 1 WHERE users.id = UserId;
$$;