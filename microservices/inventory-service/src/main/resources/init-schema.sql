DROP SCHEMA IF EXISTS "inventory" CASCADE;

CREATE SCHEMA "inventory";

DROP TABLE IF EXISTS "inventory".t_inventory CASCADE;

create table inventory.t_inventory
(
    id       bigserial constraint t_inventory_pkey primary key,
    quantity integer      not null,
    sku_code varchar(255) not null
    constraint uk_q31cjwphvuhrsgqbhf9pcerwj unique
);

