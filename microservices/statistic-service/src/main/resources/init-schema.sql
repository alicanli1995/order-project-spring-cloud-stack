DROP SCHEMA IF EXISTS "statistics" CASCADE;

CREATE SCHEMA "statistics";

create sequence native;

alter sequence native owner to postgres;

create table if not exists all_orders
(
    id               bigint       not null
        primary key,
    client_ip        varchar(255) not null,
    correlation_id   varchar(255) not null,
    create_time      timestamp,
    created_by       bigint       not null,
    deleted          boolean      not null,
    deletion_token   bigint       not null,
    last_update_time timestamp,
    last_updated_by  bigint,
    order_date       date,
    price            numeric(19, 2),
    quantity         integer,
    sku_code         varchar(255),
    total_amount     numeric(19, 2)
);

alter table all_orders
    owner to postgres;

