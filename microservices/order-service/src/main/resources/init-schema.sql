DROP SCHEMA IF EXISTS "order" CASCADE;

CREATE SCHEMA "order";

DROP TABLE IF EXISTS "order".t_order_line_items CASCADE;

create table if not exists "order".t_order_line_items
(
    id           bigserial constraint t_order_line_items_pkey primary key,
    price        numeric(19, 2),
    quantity     integer,
    sku_code     varchar(255),
    total_amount numeric(19, 2)
);

DROP TABLE IF EXISTS "order".t_orders CASCADE;

create table if not exists "order".t_orders
(
    id           bigserial constraint t_orders_pkey primary key,
    order_number varchar(255)
);

DROP TABLE IF EXISTS "order".t_orders_order_line_items_list CASCADE;

create table if not exists "order".t_orders_order_line_items_list
(
    order_id                 bigint not null constraint fk3wdgw7hxmodjqfulfaymc8aof references "order".t_orders,
    order_line_items_list_id bigint not null constraint uk_nqpoocsk2utvq7va8bgth1mj9 unique constraint fk1qq155yd0omg8y9in6526bhaj references "order".t_order_line_items
);






