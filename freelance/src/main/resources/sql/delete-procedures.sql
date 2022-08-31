CREATE OR REPLACE PROCEDURE DeleteUser(UserId BIGINT)
LANGUAGE SQL AS $$
    DELETE FROM users WHERE users.id = UserId;
$$;

CREATE OR REPLACE PROCEDURE DeleteService(ServiceId BIGINT)
LANGUAGE SQL AS $$
    DELETE FROM service_list WHERE service_list.id = ServiceId;
$$;

CREATE OR REPLACE PROCEDURE DeleteOrder(OrderId BIGINT)
LANGUAGE SQL AS $$
    DELETE FROM order_list WHERE order_list.id = OrderId;
$$;

