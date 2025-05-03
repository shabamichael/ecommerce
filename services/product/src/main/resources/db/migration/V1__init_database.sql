create table if not exists category (
    id integer not null primary key,
    description varchar(255),
    name varchar(255) not null
);


create table if not exists product(
    id integer not null primary key,
    name varchar(255) not null,
    description varchar(255),
    available_quantity double precision not null,
    price numeric(38,2) not null,
    category_id integer
        constraint fk_category
            references category(id)
);


CREATE SEQUENCE IF NOT EXISTS category_sequence START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 50;


